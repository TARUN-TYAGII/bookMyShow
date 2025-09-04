package com.example.demo.services;

import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.example.demo.models.Seat;
import com.example.demo.repositories.SeatRepository;

@Service
public class SeatService {

    private final SeatRepository seatRepository;

    public SeatService(SeatRepository seatRepository){
        this.seatRepository = seatRepository;
    }

    public Seat createSeat(Seat seat){
        return seatRepository.createSeat(seat);
    }
}
