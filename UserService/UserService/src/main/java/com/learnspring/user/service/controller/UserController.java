package com.learnspring.user.service.controller;

import com.learnspring.user.service.entities.User;
import com.learnspring.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService) {
        this.userService = userService;
    }

    int retryCount = 0;

    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    //single user get
    @GetMapping("/{userId}")
    @CircuitBreaker(name = "ratingHotelBreaker", fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getSingleUser(@PathVariable String userId){
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }

    //creating ratingHotelFallback method for circuitBreaker
    public ResponseEntity<User> ratingHotelFallback (String userId, Exception e){
        logger.info("Fallback method called due to service unavailability");
        User failedUser = User.builder()
                .email("dummyuser@yopmail.com")
                .about("This is a Dummy User for Fault Tolerance")
                .name("Dummy")
                .userId("123456789")
                .build();
        return ResponseEntity.ok(failedUser);
    }


    //all user get
    @GetMapping
    @Retry(name = "ratingHotelRetryService", fallbackMethod = "ratingHotelRetryFallback")
    public ResponseEntity<List<User>> getAllUser(){
        logger.info("Inside the getAllUser method");
        retryCount++;
        logger.info("Retry Count - {}",retryCount);
        List<User> allUser = userService.getAllUser();
        return ResponseEntity.ok(allUser);
    }

    //creating ratingHotelRetryFallback method for Retry
    public ResponseEntity<List<User>> ratingHotelRetryFallback(Exception e){
        logger.info("Retry Fallback is called due to Service unavailability");
        User failedUser = User.builder()
                .email("dummyuser@yopmail.com")
                .about("This is a Dummy User for Fault Tolerance - From Retry Method")
                .name("Dummy")
                .userId("123456789")
                .build();
        return ResponseEntity.ok(Collections.singletonList(failedUser));
    }

}
