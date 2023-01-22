package se.artcomputer.edu.hex.domain;

public class Account {
    private AccountId accountId;

    public Account(AccountId accountId) {
        this.accountId = accountId;
    }

    public AccountId getAccountId() {
        return accountId;
    }
}
