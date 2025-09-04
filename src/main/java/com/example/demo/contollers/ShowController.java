package com.example.demo.contollers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Show;
import com.example.demo.services.ShowService;

@RestController
@RequestMapping("/api/v1/show")
public class ShowController {

    private final ShowService showService;
    public ShowController(ShowService showService){
        this.showService = showService;
    }


    @PostMapping
    public ResponseEntity<Show> addShow(@RequestBody Show show){
        Show newShow = showService.addShow(show);
        return new ResponseEntity<>(newShow, HttpStatus.CREATED);
    }

    @GetMapping("/{screenId}")
    public ResponseEntity<Show> getShowByScreenId(@PathVariable Long screenId){
        Show show = showService.getShowByScreenId(screenId);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

    @GetMapping("/{movieId}")
    public ResponseEntity<Show> getShowByMovieId(@PathVariable Long movieId){
        Show show = showService.getShowByMovieId(movieId);
        return new ResponseEntity<>(show, HttpStatus.OK);
    }

}
