package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SeatRepository extends JpaRepository<Seat,Integer> {
}
