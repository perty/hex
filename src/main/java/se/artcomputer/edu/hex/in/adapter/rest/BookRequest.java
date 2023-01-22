package se.artcomputer.edu.hex.in.adapter.rest;

import java.time.LocalDate;
import java.util.List;

public record BookRequest(LocalDate bookingDate, List<BookingDto> bookings) {
}
