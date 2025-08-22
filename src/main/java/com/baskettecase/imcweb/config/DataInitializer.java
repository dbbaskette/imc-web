package com.baskettecase.imcweb.config;

import com.baskettecase.imcweb.model.Customer;
import com.baskettecase.imcweb.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private CustomerRepository customerRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // Check if data was loaded by SQL scripts
        long customerCount = customerRepository.count();
        if (customerCount > 0) {
            System.out.println("Found " + customerCount + " customers in database - data already initialized");
        } else {
            System.out.println("No customers found - SQL initialization may have failed, falling back to programmatic initialization");
            initializeCustomerData();
        }
    }
    
    private void initializeCustomerData() {
        // Create sample customers based on the customers.sql data
        Customer[] customers = {
            createCustomer(100001L, "Sarah", "Chen", "sarah.chen@email.com", "555-0101", "789 Maple Drive", "Springfield", "IL", "62704"),
            createCustomer(100002L, "Emily", "Carter", "emily.carter@email.com", "555-0102", "456 Oak Ave", "Someplace", "TX", "67890"),
            createCustomer(100003L, "Benjamin", "Rivera", "benjamin.rivera@email.com", "555-0103", "789 Pine Ln", "Elsewhere", "FL", "13579"),
            createCustomer(100004L, "Michael", "Harris", "michael.harris@email.com", "555-0104", "452 Oakwood Ave", "Columbus", "OH", "43215"),
            createCustomer(100005L, "David", "Lee", "david.lee@email.com", "555-0105", "123 Main St", "Anytown", "CA", "12345"),
            createCustomer(100006L, "Jessica", "Thompson", "jessica.thompson@email.com", "555-0106", "901 Cedar St", "Austin", "TX", "73301"),
            createCustomer(100007L, "Andrew", "Martinez", "andrew.martinez@email.com", "555-0107", "567 Birch Ave", "Denver", "CO", "80202"),
            createCustomer(100008L, "Ashley", "Wilson", "ashley.wilson@email.com", "555-0108", "234 Elm Dr", "Phoenix", "AZ", "85001"),
            createCustomer(100009L, "Christopher", "Garcia", "christopher.garcia@email.com", "555-0109", "678 Walnut Blvd", "Seattle", "WA", "98101"),
            createCustomer(100010L, "Amanda", "Rodriguez", "amanda.rodriguez@email.com", "555-0110", "345 Cherry Ln", "Miami", "FL", "33101"),
            createCustomer(100011L, "Daniel", "Johnson", "daniel.johnson@email.com", "555-0111", "890 Spruce Way", "Portland", "OR", "97201"),
            createCustomer(100012L, "Lauren", "Brown", "lauren.brown@email.com", "555-0112", "123 Aspen Ct", "Nashville", "TN", "37201"),
            createCustomer(100013L, "Matthew", "Davis", "matthew.davis@email.com", "555-0113", "456 Poplar St", "Atlanta", "GA", "30301"),
            createCustomer(100014L, "Stephanie", "Miller", "stephanie.miller@email.com", "555-0114", "789 Hickory Ave", "Boston", "MA", "02101"),
            createCustomer(100015L, "Ryan", "Anderson", "ryan.anderson@email.com", "555-0115", "012 Magnolia Dr", "San Diego", "CA", "92101")
        };
        
        for (Customer customer : customers) {
            customerRepository.save(customer);
        }
        
        System.out.println("Initialized " + customers.length + " customers in the database");
    }
    
    private Customer createCustomer(Long customerId, String firstName, String lastName, String email, 
                                   String phoneNumber, String address, String city, String state, String zipCode) {
        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setPhoneNumber(phoneNumber);
        customer.setAddress(address);
        customer.setCity(city);
        customer.setState(state);
        customer.setZipCode(zipCode);
        return customer;
    }
}
