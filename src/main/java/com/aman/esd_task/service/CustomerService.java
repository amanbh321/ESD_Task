package com.aman.esd_task.service;

import jakarta.validation.Valid;
import com.aman.esd_task.dto.CustomerLoginRequest;
import com.aman.esd_task.dto.CustomerRequest;
import com.aman.esd_task.entity.Customer;
import com.aman.esd_task.mapper.CustomerMapper;
import com.aman.esd_task.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    public String createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        repo.save(customer);
        return "Created";
    }

    public String loginCustomer(@Valid CustomerLoginRequest request) {
        Customer customer = repo.findByEmail(request.email());
        if (customer == null || !customer.getPassword().equals(request.password())) {
            return "Invalid email or password";
        }

        return "Login Successful";
    }
}
