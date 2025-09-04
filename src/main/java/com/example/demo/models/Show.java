package com.example.demo.models;


import java.util.Date;
import java.util.List;

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
public class Show {

    private Movie movie;
    private Screen screen;
    private Date startTime;
    private Date endTime;
    private Double price;
    private List<Booking> bookings;
}
