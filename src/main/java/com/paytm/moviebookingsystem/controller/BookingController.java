package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.*;
import com.paytm.moviebookingsystem.model.Booking;
import com.paytm.moviebookingsystem.model.Movie;
import com.paytm.moviebookingsystem.model.Seat;
import com.paytm.moviebookingsystem.model.Show;
import com.paytm.moviebookingsystem.services.BookingService;
import com.paytm.moviebookingsystem.services.SeatService;
import com.paytm.moviebookingsystem.services.ShowService;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private SeatService seatService;
    @Autowired
    private ShowService showService;

    @GetMapping("/bookings")
    public ResponseEntity<List<Booking>> getAllBookingss(){
        return ResponseEntity.ok(bookingService.findAll());
    }

    @GetMapping("/booking/{id}")
    public ResponseEntity<Booking> getABookingeById(@PathVariable Integer id) throws NoBookingFoundException {
        return ResponseEntity.ok(bookingService.findById(id)
                .orElseThrow(() -> new NoBookingFoundException("Booking Id: "+id+" Not Found")));
    }

    @GetMapping("/availableSeat/show")
    public ResponseEntity<List<Seat>> getAvailableSeatsOfAShow(@RequestParam Integer showId) throws NoShowFoundException {
        if(showService.findById(showId).isEmpty()){
            throw new NoShowFoundException("Show not found with id: "+showId);
        }

        List<Booking> bookings = bookingService.findByShowId(showId);

        List<Seat> availableSeats = seatService.findAll();
        if(bookings.isEmpty()){
            return ResponseEntity.ok(availableSeats);
        }
        bookings.forEach( booking -> availableSeats.remove(booking.getSeat()));
        return ResponseEntity.ok(availableSeats);
    }

    @PostMapping("/book")
    public ResponseEntity<String> book(@RequestParam Integer userId, @RequestParam Integer showId, @RequestBody List<Integer> seatIds)  {
            try{
                bookingService.bookSeats(userId, showId, seatIds);
                return ResponseEntity.ok("Seats booked successfully");
            }
            catch (NoShowFoundException | NoSeatFoundException | NoUserFoundException e){
                return ResponseEntity.badRequest().body(e.getMessage());
            }

    }
    @DeleteMapping("/cancelBooking")
    public ResponseEntity<String> cancel(@RequestBody List<Integer> bookingIds) throws NoBookingFoundException {
            bookingService.deleteBooking(bookingIds);
            return ResponseEntity.ok("Seats cancelled successfully");
    }
    @DeleteMapping("/cancelSeats")
    public ResponseEntity<String> cancelUsingUserIdAndShowIdAndSeatId(
            @RequestParam Integer userId,@RequestParam Integer showId,@RequestBody List<Integer> seatIds) throws NoBookingFoundException, NoShowFoundException, NoUserFoundException {
            bookingService.deleteBookingUsingUserIdAndShowIdAndSeatId(userId,showId,seatIds);
            return ResponseEntity.ok("Seats cancelled successfully");
    }

}
