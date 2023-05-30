package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.Booking;
import com.paytm.moviebookingsystem.model.Seat;
import com.paytm.moviebookingsystem.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Integer> {
    Optional<Booking> findById(Integer id);
    List<Booking> findByUserId(Integer userId);
    Optional<Booking> findByIdAndUserId(Integer userId, Integer id);
    List<Booking> findByShowId(Integer id);
    Boolean existsByShowIdAndSeatId(Integer seatId, Integer showId);

    Optional<Booking> findByUserIdAndShowIdAndSeatId(Integer userId, Integer showId, Integer seatId);
}
