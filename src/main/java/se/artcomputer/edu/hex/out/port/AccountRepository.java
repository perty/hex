package se.artcomputer.edu.hex.out.port;

import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.Account;
import se.artcomputer.edu.hex.domain.AccountId;

import java.util.Optional;

@Component
public interface AccountRepository {
    Optional<Account> findById(AccountId id);
}
