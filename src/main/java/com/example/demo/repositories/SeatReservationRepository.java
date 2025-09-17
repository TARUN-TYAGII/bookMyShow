package com.example.demo.repositories;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.demo.models.SeatReservation;
import com.example.demo.models.SeatStatus;

@Repository
public class SeatReservationRepository {
    private List<SeatReservation> reservations = new ArrayList<>();
    private AtomicLong idGenerator = new AtomicLong(1);

    public SeatReservation createReservation(SeatReservation reservation) {
        reservation.setId(idGenerator.getAndIncrement());
        reservation.setCreatedAt(new Date());
        reservation.setUpdatedAt(new Date());
        reservations.add(reservation);
        return reservation;
    }

    public List<SeatReservation> getReservationsByShowId(Long showId) {
        return reservations.stream()
                .filter(reservation -> reservation.getShow().getId().equals(showId))
                .filter(reservation -> !reservation.isExpired())
                .collect(Collectors.toList());
    }

    public List<SeatReservation> getReservationsBySeatAndShow(Long seatId, Long showId) {
        return reservations.stream()
                .filter(reservation -> reservation.getSeat().getId().equals(seatId))
                .filter(reservation -> reservation.getShow().getId().equals(showId))
                .filter(reservation -> !reservation.isExpired())
                .collect(Collectors.toList());
    }

    public List<SeatReservation> getReservationsByUserId(Long userId) {
        return reservations.stream()
                .filter(reservation -> reservation.getUser().getId().equals(userId))
                .filter(reservation -> !reservation.isExpired())
                .collect(Collectors.toList());
    }

    public SeatReservation updateReservation(SeatReservation reservation) {
        reservation.setUpdatedAt(new Date());
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getId().equals(reservation.getId())) {
                reservations.set(i, reservation);
                return reservation;
            }
        }
        return null;
    }

    public boolean deleteReservation(Long id) {
        return reservations.removeIf(reservation -> reservation.getId().equals(id));
    }

    public void cleanupExpiredReservations() {
        reservations.removeIf(SeatReservation::isExpired);
    }

    public List<SeatReservation> getReservationsByStatus(SeatStatus status) {
        return reservations.stream()
                .filter(reservation -> reservation.getStatus().equals(status))
                .filter(reservation -> !reservation.isExpired())
                .collect(Collectors.toList());
    }
}
