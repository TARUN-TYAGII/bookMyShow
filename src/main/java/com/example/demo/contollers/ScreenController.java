package com.example.demo.contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Screen;
import com.example.demo.services.ScreenService;

@RestController
@RequestMapping("api/v1/screen")
public class ScreenController {
    private final ScreenService screenService;

    public ScreenController(ScreenService screenService){
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<Screen> createScreen(@PathVariable Long theatreId, @RequestBody Screen screen){
        Screen newScreen = screenService.createScreen(theatreId, screen);
        return new ResponseEntity<>(newScreen, HttpStatus.CREATED);
    }

}
