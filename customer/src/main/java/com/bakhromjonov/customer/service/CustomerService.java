package com.bakhromjonov.customer.service;

import com.bakhromjonov.customer.model.Customer;
import com.bakhromjonov.customer.payload.CustomerRegistrationRequest;
import com.bakhromjonov.customer.payload.FraudCheckResponse;
import com.bakhromjonov.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final RestTemplate restTemplate;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject(
                "http://FRAUD/api/v1/fraud-check/{customerId}",
                FraudCheckResponse.class,
                customer.getId()
        );

        if (Objects.requireNonNull(fraudCheckResponse).isFraudster())
            throw new IllegalStateException("fraudster");
    }
}
