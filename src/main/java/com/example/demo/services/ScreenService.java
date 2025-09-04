package com.example.demo.services;

import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Screen;
import com.example.demo.models.Theatre;
import com.example.demo.repositories.ScreenRepository;
import com.example.demo.repositories.TheatreRepository;

@Service
public class ScreenService {
    private final ScreenRepository screenRepository;
    private final TheatreRepository theatreRepository;

    public ScreenService(ScreenRepository screenRepository, TheatreRepository theatreRepository){
        this.screenRepository = screenRepository;
        this.theatreRepository = theatreRepository;
    }


    public Screen createScreen(Long theatreId, Screen screen) {
       Optional<Theatre> theatre = theatreRepository.findTheatreById(theatreId);
       if(theatre.isEmpty()){
        throw new RuntimeException("Theatre not found");
       }
       screen.setTheatre(theatre.get());
       screen.setCreatedAt(new Date());
       screen.setUpdatedAt(new Date());
       return screenRepository.createScreen(screen);
    }

}
