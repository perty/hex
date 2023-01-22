package se.artcomputer.edu.hex.in.adapter.rest;

import java.math.BigInteger;
import java.util.Currency;

public record BookingDto(BigInteger amount,
                         Currency currency,
                         long debitAccountNumber,
                         long creditAccountNumber) {
}
