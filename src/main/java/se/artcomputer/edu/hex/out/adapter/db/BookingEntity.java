package se.artcomputer.edu.hex.out.adapter.db;

import jakarta.persistence.*;

import java.math.BigInteger;
import java.util.Currency;

@Entity
@Table(name = "booking")
public class BookingEntity {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column
    private BigInteger amount;

    @Column
    private Currency currency;

    @Column
    private Long debitAccount;

    @Column
    private Long creditAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Long getDebitAccount() {
        return debitAccount;
    }

    public void setDebitAccount(Long debitAccount) {
        this.debitAccount = debitAccount;
    }

    public Long getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(Long creditAccount) {
        this.creditAccount = creditAccount;
    }
}