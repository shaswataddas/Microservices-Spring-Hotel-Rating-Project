package com.learnspring.rating.services.impl;

import com.learnspring.rating.entities.Hotel;
import com.learnspring.rating.entities.Rating;
import com.learnspring.rating.repositories.RatingRepository;
import com.learnspring.rating.services.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;
    private RestTemplate restTemplate;

    public RatingServiceImpl(RatingRepository ratingRepository, RestTemplate restTemplate) {
        this.ratingRepository = ratingRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public Rating create(Rating rating) {
        String ratingId = UUID.randomUUID().toString();
        rating.setRatingId(ratingId);
        return ratingRepository.save(rating);
    }

    @Override
    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> getAllByUserId(String userId) {

        // Fetch ratings from DB
        List<Rating> ratings = ratingRepository.findByUserId(userId);

        // Fetch hotel details for each rating
        ratings.forEach(rating -> {

            Hotel hotel = restTemplate.getForObject(
                    "http://HOTELSERVICE/hotels/" + rating.getHotelId(),
                    Hotel.class
            );

            rating.setHotel(hotel);
        });

        return ratings;
    }

    @Override
    public List<Rating> getAllByHotelId(String hotelId) {

        // Fetch ratings from DB
        List<Rating> ratings = ratingRepository.findByHotelId(hotelId);

        if (ratings.isEmpty()) {
            return ratings;
        }

        // Fetch hotel details ONLY ONCE
        Hotel hotel = restTemplate.getForObject(
                "http://HOTELSERVICE/hotels/" + hotelId,
                Hotel.class
        );

        // Attach hotel to all ratings
        ratings.forEach(rating -> rating.setHotel(hotel));

        return ratings;
    }

}
