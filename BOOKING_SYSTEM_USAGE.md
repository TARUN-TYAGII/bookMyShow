# BookMyShow - Booking System Usage Guide

## Overview
The core booking system has been implemented with the following features:
- **Seat Availability Checking** - Check which seats are available for a show
- **Seat Reservation** - Temporarily reserve seats (10-minute timeout)
- **Booking Creation** - Create bookings with payment processing
- **Booking Confirmation** - Confirm bookings after successful payment
- **Booking Cancellation** - Cancel bookings and release seats

## API Endpoints

### 1. Check Available Seats
**GET** `/api/v1/booking/seats/available/{showId}`

Returns list of available seats for a specific show.

### 2. Reserve Seats (Temporary)
**POST** `/api/v1/booking/reserve`

Request Body:
```json
{
  "showId": 1,
  "userId": 1,
  "seatIds": [1, 2, 3]
}
```

Temporarily reserves seats for 10 minutes.

### 3. Create Booking
**POST** `/api/v1/booking/create`

Request Body:
```json
{
  "showId": 1,
  "userId": 1,
  "seatIds": [1, 2, 3]
}
```

Creates a booking in PENDING status with temporary seat reservations.

### 4. Confirm Booking
**PUT** `/api/v1/booking/confirm/{bookingId}`

Request Body:
```json
{
  "paymentReference": "PAY123456"
}
```

Confirms the booking after successful payment.

### 5. Cancel Booking
**PUT** `/api/v1/booking/cancel/{bookingId}`

Cancels the booking and releases reserved seats.

### 6. Get Booking Details
**GET** `/api/v1/booking/{bookingId}`

Returns booking details.

### 7. Get User Bookings
**GET** `/api/v1/booking/user/{userId}`

Returns all bookings for a specific user.

### 8. Get All Bookings
**GET** `/api/v1/booking`

Returns all bookings in the system.

## Booking Flow

1. **Check Available Seats**: Call GET `/api/v1/booking/seats/available/{showId}`
2. **Reserve Seats**: Call POST `/api/v1/booking/reserve` to temporarily hold seats
3. **Create Booking**: Call POST `/api/v1/booking/create` to create the booking
4. **Process Payment**: Handle payment externally
5. **Confirm Booking**: Call PUT `/api/v1/booking/confirm/{bookingId}` with payment reference

## Key Features Implemented

### Seat Status Management
- **AVAILABLE**: Seat is free to book
- **TEMPORARILY_RESERVED**: Seat is held for 10 minutes during booking process
- **BOOKED**: Seat is confirmed and booked
- **BLOCKED**: Seat is blocked by admin

### Automatic Cleanup
- Expired seat reservations are automatically cleaned up
- Reservations expire after 10 minutes if not confirmed

### Booking Status Tracking
- **PENDING**: Booking created, awaiting payment
- **CONFIRMED**: Payment successful, booking confirmed
- **CANCELLED**: Booking cancelled by user or system

## Testing the System

1. **Start the Application**: Run `./mvnw spring-boot:run`
2. **Create Test Data**:
   - Create users via `/api/v1/user`
   - Create movies via `/api/v1/movie`
   - Create theatres via `/api/v1/theatre`
   - Create screens via `/api/v1/screen` (you'll need to implement this)
   - Create seats via `/api/v1/seat/{screenId}`
   - Create shows via `/api/v1/show`

3. **Test Booking Flow**:
   - Check available seats
   - Reserve seats
   - Create booking
   - Confirm booking

## Next Steps

The core booking system is now complete. You can extend it with:
- Database integration (JPA/Hibernate)
- Real payment gateway integration
- Email notifications
- User authentication
- Advanced seat pricing
- Show timing validations
- Booking history and reports
