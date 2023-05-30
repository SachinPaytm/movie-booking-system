package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.exceptions.NoBookingFoundException;
import com.paytm.moviebookingsystem.exceptions.NoSeatFoundException;
import com.paytm.moviebookingsystem.exceptions.NoShowFoundException;
import com.paytm.moviebookingsystem.exceptions.NoUserFoundException;
import com.paytm.moviebookingsystem.model.Booking;
import com.paytm.moviebookingsystem.model.Seat;
import com.paytm.moviebookingsystem.model.Show;
import com.paytm.moviebookingsystem.model.User;
import com.paytm.moviebookingsystem.repository.BookingRepository;
import com.paytm.moviebookingsystem.repository.SeatRepository;
import com.paytm.moviebookingsystem.repository.ShowRepository;
import com.paytm.moviebookingsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private SeatRepository seatRepository;

    public List<Booking> findAll(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Integer id) {
        return bookingRepository.findById(id);
    }
    public List<Booking> findByUserId(Integer userId) {
        return bookingRepository.findByUserId(userId);
    }

    public Optional<Booking> findByUserIdAndBookingId(Integer userId, Integer bookingId) {
        return bookingRepository.findByIdAndUserId(bookingId,userId);
    }


    public synchronized void  bookSeats(Integer userId, Integer showId, List<Integer> seatIds) throws NoUserFoundException, NoShowFoundException, NoSeatFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User Id: "+userId+" Not Found"));
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NoShowFoundException("Show Id: "+showId+" Not Found"));

        List<Booking> bookings = new ArrayList<>();
        List<Seat> seatsNotAvailable = new ArrayList<>();
        for (Integer seatId : seatIds) {
            Seat seat = seatRepository.findById(seatId)
                    .orElseThrow(() -> new NoSeatFoundException("Seat Id: " + seatId + " Not Found"));
                if (bookingRepository.existsByShowIdAndSeatId(show.getId(), seatId).equals(true)) {
                    seatsNotAvailable.add(seat);
                }

                Booking booking = new Booking();
                booking.setShow(show);
                booking.setSeat(seat);
                booking.setUser(user);
                booking.setBookingDate(Date.valueOf(LocalDate.now()));
                booking.setBookingTime(Time.valueOf(LocalTime.now()));
                bookings.add(booking);
        }
        if(!seatsNotAvailable.isEmpty()){
            StringBuilder message = new StringBuilder("Seat Numbers: ");
            for (Seat seat: seatsNotAvailable){
                message.append(seat.getSeatNumber()).append(",");
            }
            message.append(" Not available");
            throw new NoSeatFoundException(message.toString());
        }
        bookingRepository.saveAll(bookings);
    }

    public synchronized void deleteBooking(List<Integer> bookingIds) throws NoBookingFoundException {

        List<Booking> bookings = new ArrayList<>();
        for (Integer bookingId : bookingIds) {
            bookings.add(bookingRepository.findById(bookingId)
                    .orElseThrow(() -> new NoBookingFoundException("Booking Id: "+bookingId+" Not Found")));
        }
        bookingRepository.deleteAll(bookings);
    }
    public synchronized   void deleteBookingUsingUserIdAndShowIdAndSeatId(Integer userId,Integer showId,List<Integer> seatIds) throws NoBookingFoundException, NoUserFoundException, NoShowFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NoUserFoundException("User Id: "+userId+" Not Found"));
        Show show = showRepository.findById(showId)
                .orElseThrow(() -> new NoShowFoundException("Show Id: "+showId+" Not Found"));

        List<Booking> bookings = new ArrayList<>();
        for (Integer seatId : seatIds) {
            Booking booking = bookingRepository.findByUserIdAndShowIdAndSeatId(userId,showId,seatId)
                    .orElseThrow(() -> new NoBookingFoundException("Booking For Seat Id: "+seatId+" Not Found"));;
            bookings.add(booking);
        }
        bookingRepository.deleteAll(bookings);
    }

    public synchronized Long count() {
        return bookingRepository.count();
    }

    public List<Booking> findByShowId(Integer showId) {
        return  bookingRepository.findByShowId(showId);
    }
}
