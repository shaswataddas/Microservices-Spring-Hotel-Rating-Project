package com.learnspring.rating.controllers;

import com.learnspring.rating.entities.Rating;
import com.learnspring.rating.services.RatingService;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public ResponseEntity<Rating> create(@RequestBody Rating rating){
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingService.create(rating));
    }

    @GetMapping
    public ResponseEntity<List<Rating>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAll());
    }

    @GetMapping("/users/{userId}")
//    @RateLimiter(name = "ratingRateLimiter", fallbackMethod = "ratingHotelFallbackRateLimiter")
    public ResponseEntity<List<Rating>> getAllByUserID(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllByUserId(userId));
    }

    public ResponseEntity<List<Rating>> ratingHotelFallbackRateLimiter (String userId, Exception e){
        Rating failedRatings = Rating.builder()
                .ratingId("123456789")
                .userId("123456789")
                .hotelId("123456789")
                .rating(-1)
                .feedback("This is a Dummy Feedback as the RateLimit gets Exceeded")
                .build();
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(Collections.singletonList(failedRatings));
    }

    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getAllByHotelID(@PathVariable String hotelId){
        return ResponseEntity.status(HttpStatus.OK).body(ratingService.getAllByHotelId(hotelId));
    }
}
