package se.artcomputer.edu.hex.in.adapter.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import se.artcomputer.edu.hex.domain.Account;
import se.artcomputer.edu.hex.domain.AccountId;
import se.artcomputer.edu.hex.in.port.AccountService;

import java.util.Optional;

@RestController
public class AccountController  {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/")
    public String hw() {
        long number = accountService.numberOfAccounts();
        return "Hello world";
    }
}
