package com.learnspring.user.service.services.impl;

import com.learnspring.user.service.entities.Hotel;
import com.learnspring.user.service.entities.Rating;
import com.learnspring.user.service.entities.User;
import com.learnspring.user.service.exceptions.ResourceNotFoundException;
import com.learnspring.user.service.external.services.HotelService;
import com.learnspring.user.service.repositories.UserRepository;
import com.learnspring.user.service.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RestTemplate restTemplate;
    private HotelService hotelService;
    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    public UserServiceImpl(UserRepository userRepository, RestTemplate restTemplate, HotelService hotelService) {
        this.userRepository = userRepository;
        this.restTemplate = restTemplate;
        this.hotelService = hotelService;
    }

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUser() {
        // Fetch all the users from DB
        List<User> allUsersList = userRepository.findAll();

        // Fetch rating for all the users


        return allUsersList;
    }

    @Override
    public User getUser(String userId) {
        // fetch user from UserService
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given Id is not found on server !! : " + userId));

        // fetch rating from rating service by passing the userId
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATINGSERVICE/ratings/users/"+user.getUserId(), Rating[].class);

        List<Rating> ratings = Arrays.stream(ratingOfUser).toList();

        List<Rating> ratingList = ratings.stream().map(rating -> {
            //api call to get Hotel details
            //http://localhost:8081/hotels/0331d16c-c94e-45f8-b04d-7a1d14496c89
//            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTELSERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
//            logger.info("response Status code : {}",forEntity.getStatusCode());

            // set hotel in the rating object
            rating.setHotel(hotel);

            // return the rating
            return rating;
        }).collect(Collectors.toList());

        // set Rating in the User object
        user.setRatings(ratingList);

        return user;
    }
}
