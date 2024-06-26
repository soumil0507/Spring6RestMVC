package bose.soumil.spring6restmvc.mappers;

import bose.soumil.spring6restmvc.entities.Customer;
import bose.soumil.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);

}
