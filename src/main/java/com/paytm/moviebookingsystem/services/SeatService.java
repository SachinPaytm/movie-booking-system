package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.model.Screen;
import com.paytm.moviebookingsystem.model.Seat;
import com.paytm.moviebookingsystem.repository.ScreenRepository;
import com.paytm.moviebookingsystem.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepository;

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    public Optional<Seat> findById(Integer seatId) {
        return seatRepository.findById(seatId);
    }


}
