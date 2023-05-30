package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.NoSeatFoundException;
import com.paytm.moviebookingsystem.model.Screen;
import com.paytm.moviebookingsystem.model.Seat;
import com.paytm.moviebookingsystem.services.ScreenService;
import com.paytm.moviebookingsystem.services.SeatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SeatController {
    @Autowired
    private SeatService seatService;

    @GetMapping("/seats")
    private ResponseEntity<List<Seat>> getAllSeat() {
        return ResponseEntity.ok(seatService.findAll());
    }

    @GetMapping("/seat/{seatId}")
    private ResponseEntity<Seat> getSeatById(@PathVariable Integer seatId) throws NoSeatFoundException {
        return ResponseEntity.ok(seatService.findById(seatId)
                .orElseThrow(() -> new NoSeatFoundException("Seat Id: "+seatId+" Not Found")));
    }

}
