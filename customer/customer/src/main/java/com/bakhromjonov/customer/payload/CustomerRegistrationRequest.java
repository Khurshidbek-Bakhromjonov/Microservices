package com.bakhromjonov.customer.payload;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
