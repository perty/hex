package se.artcomputer.edu.hex.out.port;

import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.BookingOrder;

@Component
public interface BookingRepository {
    BookingOrder save(BookingOrder bookingOrder);
}
