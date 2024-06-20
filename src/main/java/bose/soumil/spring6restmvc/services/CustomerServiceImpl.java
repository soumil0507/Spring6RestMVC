package bose.soumil.spring6restmvc.services;

import bose.soumil.spring6restmvc.model.Customer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, Customer> customerMap;

    public CustomerServiceImpl(){

        customerMap = new HashMap<>();

        Customer customer1 = Customer.builder().
                id(UUID.randomUUID()).
                name("Customer1").
                version(1).
                createdDate(LocalDateTime.now()).
                lastModifiedDate(LocalDateTime.now()).
                build();

        Customer customer2 = Customer.builder().
                id(UUID.randomUUID()).
                name("Customer2").
                version(1).
                createdDate(LocalDateTime.now()).
                lastModifiedDate(LocalDateTime.now()).
                build();

        Customer customer3 = Customer.builder().
                id(UUID.randomUUID()).
                name("Customer3").
                version(1).
                createdDate(LocalDateTime.now()).
                lastModifiedDate(LocalDateTime.now()).
                build();

        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);

    }

    @Override
    public List<Customer> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Customer getCustomerById(UUID id) {

        log.debug("Get Customer by id - in service. Id {}", id.toString());

        return customerMap.get(id);
    }
}