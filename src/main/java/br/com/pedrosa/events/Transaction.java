package br.com.pedrosa.events;

public record Transaction(
        String transactionId,
        String userId,
        double amount,
        String timestamp
) {
}

