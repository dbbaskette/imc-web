package com.baskettecase.imcweb.repository;

import com.baskettecase.imcweb.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    /**
     * Find a customer by their email address
     * @param email the customer's email
     * @return Optional containing the customer if found
     */
    Optional<Customer> findByEmail(String email);
}
