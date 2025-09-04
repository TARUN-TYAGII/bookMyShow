package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.Theatre;
import com.example.demo.repositories.TheatreRepository;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;

    public TheatreService(TheatreRepository theatreRepository){
        this.theatreRepository = theatreRepository;
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.getAllTheatres();
    }

    public Theatre getTheatreByCity(String city) {
       return theatreRepository.getTheatreByCity(city);
    }

    public Theatre createTheatre(Theatre theatre) {
        return theatreRepository.createTheatre(theatre);
    }



}
