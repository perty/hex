package se.artcomputer.edu.hex.domain;

public record Booking(Money amount, AccountId debet, AccountId credit){
}
