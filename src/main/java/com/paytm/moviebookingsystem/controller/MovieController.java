package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.NoMovieFoundException;
import com.paytm.moviebookingsystem.exceptions.NoUserFoundException;
import com.paytm.moviebookingsystem.model.Movie;
import com.paytm.moviebookingsystem.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getAllMovies(){
        return ResponseEntity.ok(movieService.findAll());
    }
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getAMovieById(@PathVariable Integer id) throws NoMovieFoundException{
        return ResponseEntity.ok(movieService.findById(id)
                .orElseThrow(() -> new NoMovieFoundException("Movie Id: "+id+" Not Found")));
    }
    @GetMapping("/movie/genre/{genre}")
    public ResponseEntity<List<Movie>> getAMovieByGenre(@PathVariable String genre){
        return ResponseEntity.ok(movieService.findByGenre(genre));
    }
    @GetMapping("/movie/language/{language}")
    public ResponseEntity<List<Movie>> getAMovieByLanguage(@PathVariable String language){
        return ResponseEntity.ok(movieService.findByLanguage(language));
    }


}
