package com.example.demo.contollers;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.models.Booking;
import com.example.demo.models.Seat;
import com.example.demo.models.SeatReservation;
import com.example.demo.services.BookingService;

@RestController
@RequestMapping("/api/v1/booking")
public class BookMyShowController {
    
    private final BookingService bookingService;

    public BookMyShowController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/seats/available/{showId}")
    public ResponseEntity<List<Seat>> getAvailableSeats(@PathVariable Long showId) {
        try {
            List<Seat> availableSeats = bookingService.getAvailableSeats(showId);
            return new ResponseEntity<>(availableSeats, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/reserve")
    public ResponseEntity<List<SeatReservation>> reserveSeats(@RequestBody Map<String, Object> request) {
        try {
            Long showId = Long.valueOf(request.get("showId").toString());
            Long userId = Long.valueOf(request.get("userId").toString());
            @SuppressWarnings("unchecked")
            List<Long> seatIds = (List<Long>) request.get("seatIds");
            
            List<SeatReservation> reservations = bookingService.reserveSeats(showId, userId, seatIds);
            return new ResponseEntity<>(reservations, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Map<String, Object> request) {
        try {
            Long showId = Long.valueOf(request.get("showId").toString());
            Long userId = Long.valueOf(request.get("userId").toString());
            @SuppressWarnings("unchecked")
            List<Long> seatIds = (List<Long>) request.get("seatIds");
            
            Booking booking = bookingService.createBooking(showId, userId, seatIds);
            return new ResponseEntity<>(booking, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/confirm/{bookingId}")
    public ResponseEntity<Booking> confirmBooking(@PathVariable Long bookingId, 
                                                 @RequestBody Map<String, String> request) {
        try {
            String paymentReference = request.get("paymentReference");
            Booking confirmedBooking = bookingService.confirmBooking(bookingId, paymentReference);
            return new ResponseEntity<>(confirmedBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/cancel/{bookingId}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long bookingId) {
        try {
            Booking cancelledBooking = bookingService.cancelBooking(bookingId);
            return new ResponseEntity<>(cancelledBooking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long bookingId) {
        try {
            Booking booking = bookingService.getBookingById(bookingId);
            if (booking == null) {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(booking, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getUserBookings(@PathVariable Long userId) {
        try {
            List<Booking> bookings = bookingService.getUserBookings(userId);
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
