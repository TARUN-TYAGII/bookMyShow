package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Show;

@Repository
public class ShowRepository {
    List<Show> shows = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public Show addShow(Show show){
        show.setId(idGenerator.getAndIncrement());
        show.setCreatedAt(new Date());
        show.setUpdatedAt(new Date());
        shows.add(show);
        return show;
    }

    public Show getShowByScreenId(Long screenId) {
        return shows.stream().filter(s -> s.getScreen().getId().equals(screenId)).findFirst().orElse(null);
        
    }

    public Show getShowByMovieId(Long movieId){
        return shows.stream().filter(s -> s.getMovie().getId().equals(movieId)).findFirst().orElse(null);
    }

    public Show getShowById(Long id) {
        return shows.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public List<Show> getAllShows() {
        return new ArrayList<>(shows);
    }
}
