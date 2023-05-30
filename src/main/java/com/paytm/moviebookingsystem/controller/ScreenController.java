package com.paytm.moviebookingsystem.controller;

import com.paytm.moviebookingsystem.exceptions.NoScreenFoundException;
import com.paytm.moviebookingsystem.model.Screen;
import com.paytm.moviebookingsystem.services.ScreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScreenController {
    @Autowired
    private ScreenService screenService;

    @GetMapping("/screens")
    private ResponseEntity<List<Screen>> getAllScreen() {
        return ResponseEntity.ok(screenService.findAll());
    }

    @GetMapping("/screen/{screenId}")
    private ResponseEntity<Screen> getScreenById(@PathVariable Integer screenId) throws NoScreenFoundException {
        return ResponseEntity.ok(screenService.findById(screenId)
                .orElseThrow(() -> new NoScreenFoundException("Screen Id: "+screenId+" Not Found")));
    }

}
