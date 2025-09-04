package com.example.demo.services;


import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.models.Screen;
import com.example.demo.models.Seat;
import com.example.demo.repositories.ScreenRepository;
import com.example.demo.repositories.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final ScreenRepository screenRepository;

    public SeatService(SeatRepository seatRepository, ScreenRepository screenRepository){
        this.seatRepository = seatRepository;
        this.screenRepository = screenRepository;
    }


    public Seat addSeat(Long screenId, Seat seat) {
        Optional<Screen> screen = screenRepository.findScreenById(screenId);
        if(screen.isEmpty()){
            throw new RuntimeException("Screen not found");
        }
        seat.setScreen(screen.get());
        return seatRepository.createSeat(seat); 
    }


    public List<Seat> getSeatsByScreenId(Long screenId) {
       return seatRepository.getSeatsByScreenId(screenId);
    }
}
