package com.example.demo.services;

import org.springframework.stereotype.Service;

import com.example.demo.models.Show;
import com.example.demo.repositories.ShowRepository;

@Service
public class ShowService {
    private final ShowRepository showRepository;

    public ShowService(ShowRepository showRepository){
        this.showRepository = showRepository;
    }

    public Show addShow(Show show){
        return showRepository.addShow(show);
    }

    public Show getShowByScreenId(Long screenId){
        return showRepository.getShowByScreenId(screenId);
    }

    public Show getShowByMovieId(Long movieId){
        return showRepository.getShowByScreenId(movieId);
    }
}
