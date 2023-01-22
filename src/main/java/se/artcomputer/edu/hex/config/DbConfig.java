package se.artcomputer.edu.hex.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import se.artcomputer.edu.hex.out.port.AccountRepository;

@Service
public class DbConfig {
    @Value("${se.artcomputer.edu.db}")
    private String dbConfig;

    @Autowired
    @Qualifier("db")
    private AccountRepository accountRepositoryDb;

    @Autowired
    @Qualifier("stub")
    private AccountRepository accountRepositoryStub;

    public AccountRepository accountRepository() {
        return switch (dbConfig) {
            case "db" -> accountRepositoryDb;
            case "stub" -> accountRepositoryStub;
            default -> throw new RuntimeException("Bad config. Set se.artcomputer.edu.db in properties.");
        };
    }
}
