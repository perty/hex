package se.artcomputer.edu.hex.out.adapter.stub;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.BookingOrder;
import se.artcomputer.edu.hex.out.port.BookingRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@Qualifier("stub")
public class BookingStubRepository implements BookingRepository {

    private final List<BookingOrder> bookingOrders = new ArrayList<>();

    @Override
    public BookingOrder save(BookingOrder bookingOrder) {
        bookingOrders.add(bookingOrder);
        return bookingOrder;
    }
}
