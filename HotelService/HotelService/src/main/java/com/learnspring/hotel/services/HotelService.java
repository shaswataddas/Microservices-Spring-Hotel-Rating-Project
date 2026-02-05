package com.learnspring.hotel.services;

import com.learnspring.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel crete(Hotel hotel);

    //get
    List<Hotel> getAll();

    //getById
    Hotel get(String id);
}
