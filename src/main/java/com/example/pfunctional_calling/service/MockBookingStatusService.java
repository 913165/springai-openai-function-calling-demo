package com.example.pfunctional_calling.service;

import java.util.Map;
import java.util.function.Function;

public class MockBookingStatusService implements Function<MockBookingStatusService.BookingRequest, MockBookingStatusService.BookingResponse> {

    public enum Status { PENDING, CONFIRMED, CANCELLED }
    public record BookingRequest(String bookingId) {}
    public record BookingResponse(String bookingId, Status status) {}

    private static final Map<String, Status> BOOKINGS = Map.of(
        "H001", Status.PENDING,
        "H002", Status.CONFIRMED,
        "H003", Status.CANCELLED
    );

    @Override
    public BookingResponse apply(BookingRequest request) {
        Status status = BOOKINGS.getOrDefault(request.bookingId(), Status.PENDING);
        return new BookingResponse(request.bookingId(), status);
    }
}
