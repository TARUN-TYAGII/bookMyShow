package com.example.demo.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.models.Booking;
import com.example.demo.models.BookingStatus;
import com.example.demo.models.Payment;
import com.example.demo.models.PaymentStatus;
import com.example.demo.models.Seat;
import com.example.demo.models.SeatReservation;
import com.example.demo.models.SeatStatus;
import com.example.demo.models.Show;
import com.example.demo.models.User;
import com.example.demo.repositories.BookingRepository;
import com.example.demo.repositories.SeatReservationRepository;
import com.example.demo.repositories.SeatRepository;
import com.example.demo.repositories.ShowRepository;
import com.example.demo.repositories.UserRepository;

@Service
public class BookingService {
    
    private final BookingRepository bookingRepository;
    private final SeatReservationRepository seatReservationRepository;
    private final SeatRepository seatRepository;
    private final ShowRepository showRepository;
    private final UserRepository userRepository;
    
    // Reservation timeout in minutes
    private static final int RESERVATION_TIMEOUT_MINUTES = 10;

    public BookingService(BookingRepository bookingRepository, 
                         SeatReservationRepository seatReservationRepository,
                         SeatRepository seatRepository,
                         ShowRepository showRepository,
                         UserRepository userRepository) {
        this.bookingRepository = bookingRepository;
        this.seatReservationRepository = seatReservationRepository;
        this.seatRepository = seatRepository;
        this.showRepository = showRepository;
        this.userRepository = userRepository;
    }

    public List<Seat> getAvailableSeats(Long showId) {
        // Clean up expired reservations first
        seatReservationRepository.cleanupExpiredReservations();
        
        Show show = showRepository.getShowById(showId);
        if (show == null) {
            throw new RuntimeException("Show not found");
        }

        // Get all seats for the screen
        List<Seat> allSeats = seatRepository.getSeatsByScreenId(show.getScreen().getId());
        
        // Get all reservations and bookings for this show
        List<SeatReservation> reservations = seatReservationRepository.getReservationsByShowId(showId);
        List<Booking> bookings = bookingRepository.getBookingsByShowId(showId);
        
        // Get reserved/booked seat IDs
        List<Long> reservedSeatIds = reservations.stream()
                .map(reservation -> reservation.getSeat().getId())
                .collect(Collectors.toList());
                
        List<Long> bookedSeatIds = bookings.stream()
                .filter(booking -> booking.getBookingStatus() == BookingStatus.CONFIRMED)
                .flatMap(booking -> booking.getSeats().stream())
                .map(Seat::getId)
                .collect(Collectors.toList());

        // Return available seats
        return allSeats.stream()
                .filter(seat -> !reservedSeatIds.contains(seat.getId()))
                .filter(seat -> !bookedSeatIds.contains(seat.getId()))
                .collect(Collectors.toList());
    }

    public List<SeatReservation> reserveSeats(Long showId, Long userId, List<Long> seatIds) {
        // Validate inputs
        Show show = showRepository.getShowById(showId);
        User user = userRepository.getUserById(userId);
        
        if (show == null) {
            throw new RuntimeException("Show not found");
        }
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // Check if seats are available
        List<Seat> availableSeats = getAvailableSeats(showId);
        List<Long> availableSeatIds = availableSeats.stream()
                .map(Seat::getId)
                .collect(Collectors.toList());

        for (Long seatId : seatIds) {
            if (!availableSeatIds.contains(seatId)) {
                throw new RuntimeException("Seat with ID " + seatId + " is not available");
            }
        }

        // Create reservations
        List<SeatReservation> reservations = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, RESERVATION_TIMEOUT_MINUTES);
        Date expirationTime = calendar.getTime();

        for (Long seatId : seatIds) {
            Seat seat = seatRepository.getSeatById(seatId);
            SeatReservation reservation = SeatReservation.builder()
                    .seat(seat)
                    .show(show)
                    .user(user)
                    .status(SeatStatus.TEMPORARILY_RESERVED)
                    .reservedAt(new Date())
                    .expiresAt(expirationTime)
                    .build();
                    
            reservations.add(seatReservationRepository.createReservation(reservation));
        }

        return reservations;
    }

    public Booking createBooking(Long showId, Long userId, List<Long> seatIds) {
        // First reserve the seats
        List<SeatReservation> reservations = reserveSeats(showId, userId, seatIds);
        
        Show show = showRepository.getShowById(showId);
        User user = userRepository.getUserById(userId);
        
        // Get seat objects
        List<Seat> seats = reservations.stream()
                .map(SeatReservation::getSeat)
                .collect(Collectors.toList());

        // Calculate total amount (simplified - using show price * number of seats)
        Double totalAmount = show.getPrice() * seats.size();

        // Create payment object
        Payment payment = Payment.builder()
                .amount(totalAmount)
                .status(PaymentStatus.PENDING)
                .build();

        // Create booking
        Booking booking = Booking.builder()
                .bookingStatus(BookingStatus.PENDING)
                .user(user)
                .show(show)
                .seats(seats)
                .payment(payment)
                .build();

        return bookingRepository.createBooking(booking);
    }

    public Booking confirmBooking(Long bookingId, String paymentReference) {
        Booking booking = bookingRepository.getBookingById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (booking.getBookingStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Booking is not in pending status");
        }

        // Simulate payment processing (in real app, integrate with payment gateway)
        booking.getPayment().setStatus(PaymentStatus.SUCCESS);
        booking.setBookingStatus(BookingStatus.CONFIRMED);

        // Update seat reservations to booked status
        List<Long> seatIds = booking.getSeats().stream()
                .map(Seat::getId)
                .collect(Collectors.toList());
                
        List<SeatReservation> reservations = seatReservationRepository
                .getReservationsByShowId(booking.getShow().getId());
                
        for (SeatReservation reservation : reservations) {
            if (seatIds.contains(reservation.getSeat().getId()) && 
                reservation.getUser().getId().equals(booking.getUser().getId())) {
                reservation.setStatus(SeatStatus.BOOKED);
                seatReservationRepository.updateReservation(reservation);
            }
        }

        return bookingRepository.updateBooking(booking);
    }

    public Booking cancelBooking(Long bookingId) {
        Booking booking = bookingRepository.getBookingById(bookingId);
        if (booking == null) {
            throw new RuntimeException("Booking not found");
        }

        if (booking.getBookingStatus() == BookingStatus.CANCELLED) {
            throw new RuntimeException("Booking is already cancelled");
        }

        // Update booking status
        booking.setBookingStatus(BookingStatus.CANCELLED);
        
        // Release seat reservations
        List<Long> seatIds = booking.getSeats().stream()
                .map(Seat::getId)
                .collect(Collectors.toList());
                
        List<SeatReservation> reservations = seatReservationRepository
                .getReservationsByShowId(booking.getShow().getId());
                
        for (SeatReservation reservation : reservations) {
            if (seatIds.contains(reservation.getSeat().getId()) && 
                reservation.getUser().getId().equals(booking.getUser().getId())) {
                seatReservationRepository.deleteReservation(reservation.getId());
            }
        }

        return bookingRepository.updateBooking(booking);
    }

    public List<Booking> getUserBookings(Long userId) {
        return bookingRepository.getBookingsByUserId(userId);
    }

    public Booking getBookingById(Long bookingId) {
        return bookingRepository.getBookingById(bookingId);
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.getAllBookings();
    }
}
