package se.artcomputer.edu.hex.domain;

import java.time.LocalDate;
import java.util.List;

public record BookingOrder (LocalDate bookingDate, List<Booking> bookings){
}
