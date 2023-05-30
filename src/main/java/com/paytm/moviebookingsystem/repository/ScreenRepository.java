package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScreenRepository  extends JpaRepository<Screen,Integer> {
}
