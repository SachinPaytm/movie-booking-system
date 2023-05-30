package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.NoBookingFoundException;
import com.paytm.moviebookingsystem.exceptions.NoUserFoundException;
import com.paytm.moviebookingsystem.model.Booking;
import com.paytm.moviebookingsystem.model.User;
import com.paytm.moviebookingsystem.services.BookingService;
import com.paytm.moviebookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private BookingService bookingService;
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.findAll());
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Integer userId) throws NoUserFoundException {
        return ResponseEntity.ok(userService.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User Id: "+userId+" Not Found")));
    }
    @GetMapping("/user/{userId}/bookings")
    public ResponseEntity<List<Booking>> getAllBookingsOfAUser(@PathVariable Integer userId) throws NoUserFoundException {
        Optional<User> user = userService.findById(userId);
        if(user.isEmpty()){
            throw new NoUserFoundException("User Id: "+userId+" Not Found");
        }

        return ResponseEntity.ok(bookingService.findByUserId(userId));
    }
    @GetMapping("/user/{userId}/booking/{bookingId}")
    public ResponseEntity<Booking> getBookingByUserIdAndBookingId(@PathVariable Integer userId, @PathVariable Integer bookingId) throws NoUserFoundException,NoBookingFoundException {
        if(userService.findById(userId).isEmpty()){
            throw new NoUserFoundException("User Id: "+userId+" Not Found");
        }
        if(bookingService.findByUserIdAndBookingId(userId,bookingId).isEmpty()){
            throw new NoBookingFoundException("Booking Id: "+bookingId+" Not Found");
        }

        return ResponseEntity.ok(bookingService.findByUserIdAndBookingId(userId,bookingId).get());
    }
}
