package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Seat;

@Repository
public class SeatRepository {
    List<Seat> seats = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);
    public Seat createSeat(Seat seat) {
        seat.setId(idGenerator.getAndIncrement());
        seat.setCreatedAt(new Date());
        seat.setUpdatedAt(new Date());
        seats.add(seat);
        return seat;
    }

    
}
