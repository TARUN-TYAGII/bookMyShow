package com.example.demo.contollers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Theatre;
import com.example.demo.services.TheatreService;

@RestController
@RequestMapping("/api/v1/theatre")
public class TheatreController {

    private final TheatreService theatreService;

    public TheatreController(TheatreService theatreService){
        this.theatreService = theatreService;
    }

    @GetMapping
    public ResponseEntity<List<Theatre>> getAllTheatres() {
        List<Theatre> theatres = theatreService.getAllTheatres();
        return new ResponseEntity<>(theatres, HttpStatus.OK);
    }

    @GetMapping("/{city}")
    public ResponseEntity<Theatre> getTheatreByCity(@PathVariable String city) {
        Theatre theatre = theatreService.getTheatreByCity(city);
        return new ResponseEntity<>(theatre, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Theatre> createTheatre(@RequestBody Theatre theatre) {
        Theatre newTheatre = theatreService.createTheatre(theatre);
        return new ResponseEntity<>(newTheatre, HttpStatus.CREATED);
    }

}
