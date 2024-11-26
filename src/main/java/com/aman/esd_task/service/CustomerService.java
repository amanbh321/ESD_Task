package com.aman.esd_task.service;

import com.aman.esd_task.dto.*;
import com.aman.esd_task.entity.Customer;
import com.aman.esd_task.exception.CustomerNotFound;
import com.aman.esd_task.helper.EncryptionService;
import com.aman.esd_task.helper.JWTHelper;
import com.aman.esd_task.mapper.CustomerMapper;
import com.aman.esd_task.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo repo;
    private final CustomerMapper mapper;
    private final EncryptionService encryptionService;
    private final JWTHelper jwtHelper;

    public String createCustomer(CustomerRequest request) {
        Customer customer = mapper.toEntity(request);
        System.out.println("Creating customer");
        String encryptedPassword = encryptionService.encryptPassword(customer.getPassword());
        System.out.println("Password encrypted");
        customer.setPassword(encryptedPassword);
        repo.save(customer);
        return "Customer created successfully";
    }

    public LoginResponse loginCustomer(LoginRequest request) {
        Customer customer = repo.findByEmail(request.email());
        if (customer == null) {
            return new LoginResponse(false, null);
        }

        String enteredPass = request.password();
        String encryptedPass = customer.getPassword();

        if (!encryptionService.verifyPassword(enteredPass, encryptedPass)) {
            return new LoginResponse(false, null);
        }

        String token = jwtHelper.generateToken(customer.getEmail());

        return new LoginResponse(true, token);
    }

    public CustomerResponse getCustomerDetails(String authorization) {
        String username = jwtHelper.getUsernameFromAuthHeader(authorization);
        Customer customer = repo.findByEmail(username);
        if (customer == null) {
            throw new CustomerNotFound("Customer with the given email not found");
        }
        return mapper.entityToResponse(customer);
    }

    public CustomerResponse updateCustomerDetails(String authHeader, CustomerUpdateRequest request) {
        String email = jwtHelper.getUsernameFromAuthHeader(authHeader);
        Customer customer = repo.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Customer with the given email not found");
        }

        customer.setCity(request.city());
        customer.setAddress(request.address());
        customer.setPincode(request.pincode());
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());

        Customer updatedCustomer = repo.save(customer);

        return mapper.entityToResponse(updatedCustomer);
    }

    public void deleteCustomer(String authorization) {
        String email = jwtHelper.getUsernameFromAuthHeader(authorization);
        Customer customer = repo.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFound("Customer with the given email not found");
        }
        repo.delete(customer);
    }
}
