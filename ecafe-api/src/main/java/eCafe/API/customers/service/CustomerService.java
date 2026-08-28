package eCafe.API.customers.service;
import eCafe.API.customers.dto.CustomerUpdateRequest;
import org.springframework.security.core.Authentication;

import eCafe.API.customers.dto.CustomerRequest;
import eCafe.API.customers.dto.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse create(CustomerRequest request);

    CustomerResponse update(Long id, CustomerUpdateRequest request);

    CustomerResponse findCustomerById(Long id);

    List<CustomerResponse> findAll();

    void deleteById(Long id);

    CustomerResponse findCurrentCustomer(Authentication authentication);
}
