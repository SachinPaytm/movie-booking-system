package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.Show;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShowRepository extends JpaRepository<Show,Integer> {
}
