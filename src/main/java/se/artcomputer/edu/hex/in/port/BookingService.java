package se.artcomputer.edu.hex.in.port;

import org.springframework.stereotype.Service;
import se.artcomputer.edu.hex.config.DbConfig;
import se.artcomputer.edu.hex.domain.BookingOrder;

@Service
public class BookingService {
    private final DbConfig dbConfig;

    public BookingService(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public BookingOrder book(BookingOrder bookingOrder) {
        return dbConfig.bookingRepository().save(bookingOrder);
    }
}
