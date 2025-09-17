package com.example.demo.models;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatReservation extends BaseModel {
    private Seat seat;
    private Show show;
    private User user;
    private SeatStatus status;
    private Date reservedAt;
    private Date expiresAt;
    
    // Helper method to check if reservation is expired
    public boolean isExpired() {
        return new Date().after(expiresAt);
    }
}
