package com.example.demo.models;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class Movie extends BaseModel {
    private String title;
    private String genre;
    private String duration;
    private String language;
}
