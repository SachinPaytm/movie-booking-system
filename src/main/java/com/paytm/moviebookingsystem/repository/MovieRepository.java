package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MovieRepository  extends JpaRepository<Movie,Integer> {
    List<Movie> findAll();
    List<Movie> findByGenre(String genre);
    List<Movie> findByLanguage(String language);
}
