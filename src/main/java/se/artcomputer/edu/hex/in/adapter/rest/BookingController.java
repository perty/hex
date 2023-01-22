package se.artcomputer.edu.hex.in.adapter.rest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.edu.hex.domain.AccountId;
import se.artcomputer.edu.hex.domain.Booking;
import se.artcomputer.edu.hex.domain.BookingOrder;
import se.artcomputer.edu.hex.domain.Money;
import se.artcomputer.edu.hex.in.port.BookingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookingController {
    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/book")
    public BookingOrderDto createBooking(@RequestBody BookRequest bookRequest) {
        return toDto(bookingService.book(fromRequest(bookRequest)));
    }

    private BookingOrder fromRequest(BookRequest bookRequest) {
        return new BookingOrder(bookRequest.bookingDate(), fromDtos(bookRequest.bookings()));
    }

    private List<Booking> fromDtos(List<BookingDto> bookings) {
        return bookings.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }

    private Booking fromDto(BookingDto bookingDto) {
        return new Booking(
                new Money(bookingDto.amount(), bookingDto.currency()),
                new AccountId(bookingDto.debitAccountNumber()),
                new AccountId(bookingDto.creditAccountNumber()));
    }

    private BookingOrderDto toDto(BookingOrder bookingOrder) {
        return new BookingOrderDto(bookingOrder.bookingDate(), toDtos(bookingOrder.bookings()));
    }

    private List<BookingDto> toDtos(List<Booking> bookings) {
        return bookings.stream()
                .map(this::toDto)
                .collect(Collectors.toList());

    }

    private BookingDto toDto(Booking booking) {
        return new BookingDto(booking.amount().amount(),
                booking.amount().currency(),
                booking.debet().value(),
                booking.credit().value());
    }
}
