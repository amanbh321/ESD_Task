package com.aman.esd_task.mapper;

import com.aman.esd_task.dto.CustomerRequest;
import com.aman.esd_task.dto.CustomerResponse;
import com.aman.esd_task.entity.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .password(request.password())
                .address(request.address())
                .city(request.city())
                .pincode(request.pincode())
                .build();
    }

    public CustomerResponse entityToResponse(Customer customer) {
        return CustomerResponse.builder()
                .firstName(customer.getFirstName())
                .email(customer.getEmail())
                .lastName(customer.getLastName())
                .pincode(customer.getPincode())
                .address(customer.getAddress())
                .city(customer.getCity())
                .build();
    }
}
