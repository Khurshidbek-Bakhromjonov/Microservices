package com.bakhromjonov.customer.service;

import com.bakhromjonov.amqp.RabbitMQMessageProducer;
import com.bakhromjonov.clients.fraud.FraudCheckResponse;
import com.bakhromjonov.clients.fraud.FraudClient;
import com.bakhromjonov.clients.notification.NotificationRequest;
import com.bakhromjonov.customer.model.Customer;
import com.bakhromjonov.customer.payload.CustomerRegistrationRequest;
import com.bakhromjonov.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster())
            throw new IllegalStateException("fraudster");

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi %s, welcome to Bakhromjonov's...", customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                "internal.notification.routing-key");
    }
}
