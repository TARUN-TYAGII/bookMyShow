package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.models.Booking;
import com.example.demo.models.BookingStatus;

@Repository
public class BookingRepository {
    private List<Booking> bookings = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }

    public Booking getBookingById(Long id) {
        return bookings.stream()
                .filter(booking -> booking.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<Booking> getBookingsByUserId(Long userId) {
        return bookings.stream()
                .filter(booking -> booking.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Booking> getBookingsByShowId(Long showId) {
        return bookings.stream()
                .filter(booking -> booking.getShow().getId().equals(showId))
                .collect(Collectors.toList());
    }

    public Booking createBooking(Booking booking) {
        booking.setId(idGenerator.getAndIncrement());
        booking.setCreatedAt(new Date());
        booking.setUpdatedAt(new Date());
        bookings.add(booking);
        return booking;
    }

    public Booking updateBooking(Booking booking) {
        booking.setUpdatedAt(new Date());
        for (int i = 0; i < bookings.size(); i++) {
            if (bookings.get(i).getId().equals(booking.getId())) {
                bookings.set(i, booking);
                return booking;
            }
        }
        return null;
    }

    public boolean deleteBooking(Long id) {
        return bookings.removeIf(booking -> booking.getId().equals(id));
    }

    public List<Booking> getBookingsByStatus(BookingStatus status) {
        return bookings.stream()
                .filter(booking -> booking.getBookingStatus().equals(status))
                .collect(Collectors.toList());
    }
}
