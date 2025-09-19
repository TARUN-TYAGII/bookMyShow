package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Screen;

@Repository
public class ScreenRepository {
    List<Screen> screens = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public Screen createScreen(Screen screen) {
        screen.setId(idGenerator.getAndIncrement());
        screen.setCreatedAt(new Date());
        screen.setUpdatedAt(new Date());
        screens.add(screen);
        return screen;
    }

    public Optional<Screen> findScreenById(Long screenId) {
       Optional<Screen> screen = screens.stream().filter(s -> s.getId().equals(screenId)).findFirst();
       return screen;
    }

}
