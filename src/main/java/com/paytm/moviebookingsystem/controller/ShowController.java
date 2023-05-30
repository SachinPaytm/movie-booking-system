package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.NoBookingFoundException;
import com.paytm.moviebookingsystem.exceptions.NoShowFoundException;
import com.paytm.moviebookingsystem.exceptions.NoUserFoundException;
import com.paytm.moviebookingsystem.model.*;
import com.paytm.moviebookingsystem.services.BookingService;
import com.paytm.moviebookingsystem.services.ShowService;
import com.paytm.moviebookingsystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ShowController {
    @Autowired
    private ShowService showService;
    @GetMapping("/shows")
    public ResponseEntity<List<Show>> getAllShows(){
        return ResponseEntity.ok(showService.findAll());
    }
    @GetMapping("/show/{id}")
    public ResponseEntity<Show> getAShow(@PathVariable Integer id) throws NoShowFoundException {
        return ResponseEntity.ok(showService.findById(id)
                .orElseThrow(() -> new NoShowFoundException("Show Id: "+id+" Not Found")));
    }
}
