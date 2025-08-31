package com.example.demo.models;


import lombok.experimental.SuperBuilder;

@SuperBuilder
public class User extends BaseModel {
    private String name;
    private String email;
    private String phoneNumber;
}
