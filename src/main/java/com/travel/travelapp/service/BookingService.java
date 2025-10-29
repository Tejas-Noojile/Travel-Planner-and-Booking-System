package com.travel.travelapp.service;

import com.travel.travelapp.dto.BookingDTO;
import java.util.List;

public interface BookingService {
    BookingDTO createBooking(BookingDTO dto);

    BookingDTO getBookingById(String bookingId);

    List<BookingDTO> getBookingsByUserId(String userId);

    List<BookingDTO> getBookingsByTripId(String tripId);

    BookingDTO updateBooking(String bookingId, BookingDTO dto);

    void deleteBooking(String bookingId);
}