package se.artcomputer.edu.hex.out.adapter.db;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import se.artcomputer.edu.hex.domain.Account;
import se.artcomputer.edu.hex.domain.AccountId;
import se.artcomputer.edu.hex.out.port.AccountRepository;

import java.util.Optional;

@Component
@Qualifier("db")
public class AccountEntityRepository implements AccountRepository {

    private final AccountJpaRepository accountJpaRepository;

    public AccountEntityRepository(AccountJpaRepository accountJpaRepository) {
        this.accountJpaRepository = accountJpaRepository;
    }

    @Override
    public Optional<Account> findById(AccountId id) {
        Optional<AccountEntity> accountEntityOptional = accountJpaRepository.findById(id.value());
        return accountEntityOptional.map(ae -> mapToDomain(ae));
    }

     private Account mapToDomain(AccountEntity ae) {
          return new Account(new AccountId(ae.getId()));
     }
}
