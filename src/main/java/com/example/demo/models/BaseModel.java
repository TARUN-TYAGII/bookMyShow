package com.example.demo.models;


import java.util.Date;

import lombok.experimental.SuperBuilder;

@SuperBuilder
public class BaseModel {
    private Long id;
    private Date createdAt;
    private Date updatedAt;
}
