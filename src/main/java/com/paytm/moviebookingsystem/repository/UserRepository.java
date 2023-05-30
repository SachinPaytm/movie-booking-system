package com.paytm.moviebookingsystem.repository;

import com.paytm.moviebookingsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Integer> {
}
