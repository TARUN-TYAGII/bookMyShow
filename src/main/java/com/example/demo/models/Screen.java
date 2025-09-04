package com.example.demo.models;

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
public class Screen extends BaseModel {
    private String name;
    private List<Seat> seats;
    private List<Show> shows;
    private Theatre theatre;
}
