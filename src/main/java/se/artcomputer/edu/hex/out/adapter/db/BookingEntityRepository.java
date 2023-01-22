package se.artcomputer.edu.hex.out.adapter.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.AccountId;
import se.artcomputer.edu.hex.domain.Booking;
import se.artcomputer.edu.hex.domain.BookingOrder;
import se.artcomputer.edu.hex.domain.Money;
import se.artcomputer.edu.hex.out.port.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Qualifier("db")
public class BookingEntityRepository implements BookingRepository {

    private final BookingOrderJpaRepository bookingOrderJpaRepository;

    public BookingEntityRepository(BookingOrderJpaRepository bookingOrderJpaRepository) {
        this.bookingOrderJpaRepository = bookingOrderJpaRepository;
    }


    @Override
    public BookingOrder save(BookingOrder bookingOrder) {
        return toDomain(this.bookingOrderJpaRepository.save(fromDomain(bookingOrder)));
    }

    private BookingOrder toDomain(BookingOrderEntity save) {
        return new BookingOrder(save.getBookingDate(), toDomains(save.getBookings()));
    }

    private List<Booking> toDomains(List<BookingEntity> bookingEntities) {
        return bookingEntities.stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private Booking toDomain(BookingEntity bookingEntity) {
        return new Booking(new Money(bookingEntity.getAmount(), bookingEntity.getCurrency()),
                new AccountId(bookingEntity.getDebitAccount()),
                new AccountId(bookingEntity.getCreditAccount())
        );
    }

    private BookingOrderEntity fromDomain(BookingOrder bookingOrder) {
        BookingOrderEntity bookingOrderEntity = new BookingOrderEntity();
        bookingOrderEntity.setBookingDate(bookingOrder.bookingDate());
        bookingOrderEntity.setBookings(fromDomains(bookingOrder.bookings()));
        return bookingOrderEntity;
    }

    private List<BookingEntity> fromDomains(List<Booking> bookings) {
        return bookings.stream()
                .map(this::fromDomain)
                .collect(Collectors.toList());
    }

    private BookingEntity fromDomain(Booking booking) {
        BookingEntity bookingEntity = new BookingEntity();
        bookingEntity.setAmount(booking.amount().amount());
        bookingEntity.setCurrency(booking.amount().currency());
        bookingEntity.setDebitAccount(booking.debet().value());
        bookingEntity.setCreditAccount(booking.credit().value());
        return bookingEntity;
    }
}
