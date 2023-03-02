package com.bakhromjonov.customer.service;

import com.bakhromjonov.customer.model.Customer;
import com.bakhromjonov.customer.payload.CustomerRegistrationRequest;
import com.bakhromjonov.customer.repository.CustomerRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public record CustomerService(CustomerRepository customerRepository) {

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.save(customer);
    }
}
