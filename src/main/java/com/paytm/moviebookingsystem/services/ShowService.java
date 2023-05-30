package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.model.Show;
import com.paytm.moviebookingsystem.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    public List<Show> findAll(){
        return showRepository.findAll();
    }

    public Optional<Show> findById(Integer id){
        return showRepository.findById(id);
    }
}
