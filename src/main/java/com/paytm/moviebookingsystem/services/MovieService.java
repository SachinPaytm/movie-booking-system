package com.paytm.moviebookingsystem.services;

import com.paytm.moviebookingsystem.model.Movie;
import com.paytm.moviebookingsystem.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Integer id) {
        return movieRepository.findById(id);
    }

    public List<Movie> findByGenre(String genre) {
        return movieRepository.findByGenre(genre);

    }
    public List<Movie> findByLanguage(String language) {
        return movieRepository.findByLanguage(language);
    }
}
