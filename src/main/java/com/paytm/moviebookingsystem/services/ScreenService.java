package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.model.*;
import com.paytm.moviebookingsystem.repository.ScreenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ScreenService {

    @Autowired
    private ScreenRepository screenRepository;

    public List<Screen> findAll() {
        return screenRepository.findAll();
    }

    public Optional<Screen> findById(Integer screenId) {
        return screenRepository.findById(screenId);
    }


}
