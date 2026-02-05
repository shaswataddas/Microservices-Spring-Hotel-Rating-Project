package com.learnspring.rating.services;

import com.learnspring.rating.entities.Rating;

import java.util.List;

public interface RatingService {
    //create
    Rating create(Rating rating);

    //get all rating
    List<Rating> getAll();

    //get all by UserId
    List<Rating> getAllByUserId(String userId);

    //get all by HotelId
    List<Rating> getAllByHotelId(String hotelId);
}
