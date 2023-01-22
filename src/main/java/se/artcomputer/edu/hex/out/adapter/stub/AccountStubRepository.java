package se.artcomputer.edu.hex.out.adapter.stub;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.Account;
import se.artcomputer.edu.hex.domain.AccountId;
import se.artcomputer.edu.hex.out.port.AccountRepository;

import java.util.Optional;

@Component
@Qualifier("stub")
public class AccountStubRepository implements AccountRepository {
    @Override
    public Optional<Account> findById(AccountId id) {
        return Optional.empty();
    }
}
