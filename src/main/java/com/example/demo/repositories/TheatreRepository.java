package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Theatre;

@Repository
public class TheatreRepository {
    List<Theatre> theatres = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<Theatre> getAllTheatres(){
        return new ArrayList<>(theatres);
    }

    public Theatre getTheatreByCity(String city){
        Theatre theatre = theatres.stream().filter(t-> t.getCity().equals(city)).findFirst().orElse(null);
        return theatre;
    }

    public Theatre createTheatre(Theatre theatre){
        theatre.setId(idGenerator.getAndIncrement());
        theatre.setCreatedAt(new Date());
        theatre.setUpdatedAt(new Date());
        theatres.add(theatre);
        return theatre;
    }

    public Optional<Theatre> findTheatreById(Long theatreId) {
        return theatres.stream().filter(t-> t.getId().equals(theatreId)).findFirst();
    }
    

}
