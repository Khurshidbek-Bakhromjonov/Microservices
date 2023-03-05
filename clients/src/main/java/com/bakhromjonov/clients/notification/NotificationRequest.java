package com.bakhromjonov.clients.notification;

public record NotificationRequest(
        Long toCustomerId,
        String toCustomerName,
        String message
) {
}
