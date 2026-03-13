package org.globex.retail.store.customer.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import org.globex.retail.store.customer.model.dto.CustomerDto;
import org.globex.retail.store.customer.model.dto.CustomerMapper;
import org.globex.retail.store.customer.model.entity.Customer;

@ApplicationScoped
public class CustomerService {

    @Transactional
    public CustomerDto getCustomerByCustomerId(String userId) {
        return CustomerMapper.toDto(Customer.findByUserId(userId));
    }

    @Transactional
    public CustomerDto getCustomerByEmail(String email) {
        return CustomerMapper.toDto(Customer.findByEmail(email));
    }

}
