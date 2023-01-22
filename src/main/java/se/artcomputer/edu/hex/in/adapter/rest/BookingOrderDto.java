package se.artcomputer.edu.hex.in.adapter.rest;

import java.time.LocalDate;
import java.util.List;

public record BookingOrderDto(LocalDate bookingDate, List<BookingDto> bookings) {
}
