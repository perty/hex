package se.artcomputer.edu.hex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import se.artcomputer.edu.hex.out.port.BookingRepository;

@Service
public class DbConfig {
    @Value("${se.artcomputer.edu.db}")
    private String dbConfig;
    @Autowired
    @Qualifier("db")
    private BookingRepository bookingRepositoryDb;
    @Autowired
    @Qualifier("stub")
    private BookingRepository bookingRepositoryStub;

    public BookingRepository bookingRepository() {
        return switch (dbConfig) {
            case "db" -> bookingRepositoryDb;
            case "stub" -> bookingRepositoryStub;
            default -> throw new RuntimeException("Bad config. Set se.artcomputer.edu.db in properties.");
        };
    }
}
