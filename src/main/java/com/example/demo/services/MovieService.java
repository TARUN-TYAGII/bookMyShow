package com.example.demo.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.models.Movie;
import com.example.demo.repositories.MovieRepository;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.getAllMovies();
        
    }

    public Movie getMovieById(Long id) {
      return movieRepository.getMovieById(id);
    }

    public Movie createMovie(Movie movie) {
        return movieRepository.createMovie(movie);
    }

}
