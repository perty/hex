package se.artcomputer.edu.hex.in.port;

import org.springframework.stereotype.Service;
import se.artcomputer.edu.hex.config.DbConfig;
import se.artcomputer.edu.hex.domain.Account;
import se.artcomputer.edu.hex.domain.AccountId;

import java.util.Optional;

@Service
public class AccountService {
    private final DbConfig dbConfig;

    public AccountService(DbConfig dbConfig) {
        this.dbConfig = dbConfig;
    }

    public long numberOfAccounts() {
        Optional<Account> accountOptional = dbConfig.accountRepository().findById(new AccountId(42L));
        return 0;
    }
}
