package bose.soumil.spring6restmvc.services;

import bose.soumil.spring6restmvc.model.CustomerDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl(){

        customerMap = new HashMap<>();

        CustomerDTO customer1 = CustomerDTO.builder().
                id(UUID.randomUUID()).
                name("Customer1").
                version(1).
                createdDate(LocalDateTime.now()).
                lastModifiedDate(LocalDateTime.now()).
                build();

        CustomerDTO customer2 = CustomerDTO.builder().
                id(UUID.randomUUID()).
                name("Customer2").
                version(1).
                createdDate(LocalDateTime.now()).
                lastModifiedDate(LocalDateTime.now()).
                build();

        CustomerDTO customer3 = CustomerDTO.builder().
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

//    get request
    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {

        log.debug("Get Customer by id - in service. Id {}", id.toString());

        return Optional.of(customerMap.get(id));
    }

//    post reqeust
    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO
                .builder()
                .id(UUID.randomUUID())
                .version(1)
                .createdDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .name(customer.getName())
                .build();

        customerMap.put(savedCustomer.getId(), savedCustomer);

        return savedCustomer;
    }

//    put request
    @Override
    public void updateCustomer(UUID customerId, CustomerDTO customer) {
//        get hold of the existing customer
        CustomerDTO existingCustomer = customerMap.get(customerId);

//        update the customer details
        existingCustomer.setName(customer.getName());

//        add the updatedCustomer in the customer map
        customerMap.put(existingCustomer.getId(), existingCustomer);
    }

//    delete request
    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

//    patch request

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customer) {

        CustomerDTO existingCustomer = customerMap.get(customerId);

        if (StringUtils.hasText(customer.getName())){
            existingCustomer.setName(customer.getName());
        }

    }
}
