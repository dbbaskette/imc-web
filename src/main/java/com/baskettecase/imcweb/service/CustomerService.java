package com.baskettecase.imcweb.service;

import com.baskettecase.imcweb.model.Customer;
import com.baskettecase.imcweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    /**
     * Authenticate a customer using their email.
     * Password is ignored for demo purposes.
     * 
     * @param email the customer's email
     * @param password the password (ignored for demo)
     * @return Optional containing the customer if found
     */
    public Optional<Customer> authenticateCustomer(String email, String password) {
        // For demo purposes, we ignore the password and just look up by email
        return customerRepository.findByEmail(email);
    }
    
    /**
     * Get a customer by their email
     * 
     * @param email the customer's email
     * @return Optional containing the customer if found
     */
    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
