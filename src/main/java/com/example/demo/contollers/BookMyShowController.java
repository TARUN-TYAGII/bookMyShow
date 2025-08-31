package com.example.demo.contollers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/api/v1/bookShow")
public class BookMyShowController {

    @GetMapping("path")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    

    @PostMapping()
    public ResponseEntity<String> postMethodName(@RequestBody String entity) {       
        return new ResponseEntity<>("Booking created", HttpStatus.OK);
    }
    
}
