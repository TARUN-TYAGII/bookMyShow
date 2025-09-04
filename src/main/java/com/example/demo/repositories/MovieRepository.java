package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Movie;

@Repository
public class MovieRepository {
    List<Movie> movies = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<Movie> getAllMovies() {
        return new ArrayList<>(movies);
    }

    public Movie getMovieById(Long id) {
        Movie movie = movies.stream().filter(m -> m.getId().equals(id)).findFirst().orElse(null);
        return movie;
    }

    public Movie createMovie(Movie movie) {
        movie.setId(idGenerator.getAndIncrement());
        movie.setCreatedAt(new Date());
        movie.setUpdatedAt(new Date());
        movies.add(movie);
        return movie;
       
    }

}
