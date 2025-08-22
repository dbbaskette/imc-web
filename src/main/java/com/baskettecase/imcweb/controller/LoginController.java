package com.baskettecase.imcweb.controller;

import com.baskettecase.imcweb.model.Customer;
import com.baskettecase.imcweb.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
    
    @Autowired
    private CustomerService customerService;
    
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginRequest) {
        String email = loginRequest.get("email");
        String password = loginRequest.get("password");
        
        Map<String, Object> response = new HashMap<>();
        
        if (email == null || email.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Email is required");
            return ResponseEntity.badRequest().body(response);
        }
        
        Optional<Customer> customerOpt = customerService.authenticateCustomer(email, password);
        
        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("customer", Map.of(
                "customerId", customer.getCustomerId(),
                "firstName", customer.getFirstName(),
                "lastName", customer.getLastName(),
                "email", customer.getEmail()
            ));
            return ResponseEntity.ok(response);
        } else {
            response.put("success", false);
            response.put("message", "Customer not found with email: " + email);
            return ResponseEntity.status(401).body(response);
        }
    }
}
