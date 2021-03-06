package com.customer.service.impl;

import com.customer.exception.CustomerNotFoundException;
import com.customer.exception.EmailExistsException;
import com.customer.model.Customer;
import com.customer.repository.CustomerRepository;
import com.customer.service.CustomerService;
import io.reactivex.Maybe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Maybe<List<Customer>> getAllCustomer() {
        return Maybe.fromCallable(() -> {
            List<Customer> customers = this.customerRepository.findAll();
            return customers;
        });
    }

    @Override
    public Maybe<Customer> getCustomerById(Long customerId) {
        return Maybe.fromCallable(() -> {
            Customer customer = customerRepository.findOne(customerId);
            if (customer == null) {
                throw new CustomerNotFoundException("Customer not found");
            }
            return customer;
        });
    }

    @Override
    public Maybe<Customer> addCustomer(Customer customer) {
        return Maybe.fromCallable(() -> {
            // Check if email exist.
            boolean isEmailExist = this.customerRepository.existsByEmail(customer.getEmail());
            if (isEmailExist) {
                throw new EmailExistsException("Email already exist");
            }
            Customer c = this.customerRepository.save(customer);
            return c;
        });
    }

    @Override
    public Maybe<Customer> updateCustomerById(Long customerId, Customer customer) {
        return Maybe.fromCallable(() -> {
            Customer c = this.customerRepository.findOne(customerId);
            if (c == null) {
                throw new CustomerNotFoundException("Customer not found");
            }
            // Update customer.
            c.setFirstname(customer.getFirstname());
            c.setLastname(customer.getLastname());
            c.setAge(customer.getAge());
            this.customerRepository.save(c);
            return c;
        });
    }

    @Override
    public Maybe<Void> deleteCustomerById(Long customerId) {
        return Maybe.fromCallable(() -> {
            if (!this.customerRepository.exists(customerId)) {
                throw new CustomerNotFoundException("Customer not found");
            }
            this.customerRepository.delete(customerId);
            return null;
        });
    }

    @Override
    public Maybe<List<Customer>> getCustomersByAge(int age) {
        return Maybe.fromCallable(() -> {
            List<Customer> customers = this.customerRepository.findAllByAge(age);
            return customers;
        });
    }
}
