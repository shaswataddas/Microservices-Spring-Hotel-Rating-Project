package com.learnspring.hotel.controllers;

import com.learnspring.hotel.entities.Hotel;
import com.learnspring.hotel.services.HotelService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
public class HotelController {

    private HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    //create Hotel
    @PostMapping
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel){
        Hotel hotel1 = hotelService.crete(hotel);
        return ResponseEntity.status(HttpStatus.CREATED).body(hotel1);
    }

    //get All Hotel
    @GetMapping()
    public ResponseEntity<List<Hotel>> getAllHotel() {
        List<Hotel> hotels = hotelService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(hotels);
    }

    //get single Hotel
    @GetMapping("/{hotelId}")
    public ResponseEntity<Hotel> getHotelById(@PathVariable String hotelId){
        Hotel hotel = hotelService.get(hotelId);
        return ResponseEntity.status(HttpStatus.OK).body(hotel);
    }
}
