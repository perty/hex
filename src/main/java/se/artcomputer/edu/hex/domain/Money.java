package se.artcomputer.edu.hex.domain;

import java.math.BigInteger;
import java.util.Currency;

public record Money(BigInteger amount, Currency currency) {
}
