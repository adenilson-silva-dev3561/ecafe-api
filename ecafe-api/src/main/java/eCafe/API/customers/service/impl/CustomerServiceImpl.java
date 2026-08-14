package eCafe.API.customers.service.impl;

import eCafe.API.common.constants.ExceptionMessages;
import eCafe.API.common.constants.LogMessages;
import eCafe.API.common.exception.ResourceNotFoundException;
import eCafe.API.customers.dto.CustomerRequest;
import eCafe.API.customers.dto.CustomerResponse;
import eCafe.API.customers.entity.Customer;
import eCafe.API.customers.repository.CustomerRepository;
import eCafe.API.customers.service.CustomerService;
import eCafe.API.customers.service.KeycloakService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final KeycloakService keycloakService;

    @Override
    public CustomerResponse create(CustomerRequest request) {

        log.info(LogMessages.CUSTOMER_CREATE_START, request.name());

        if (customerRepository.existsByCpf(request.cpf())) {

            log.warn(LogMessages.CUSTOMER_ALREADY_EXISTS, request.cpf());
            throw new RuntimeException(ExceptionMessages.CUSTOMER_ALREADY_EXISTS + request.cpf());
        }

        String keycloakUserId = keycloakService.createUser(
                request.email(),
                request.email(),
                request.password()
        );

        Customer customer = Customer.builder()
                .name(request.name())
                .email(request.email())
                .cpf(request.cpf())
                .phone(request.phone())
                .createdAt(LocalDate.now())
                .birthDate(request.birth_date())
                .active(true)
                .keycloakUserId(keycloakUserId)
                .build();

        Customer customerSaved = customerRepository.save(customer);

        log.info(LogMessages.CUSTOMER_CREATE_SUCCESS, customerSaved.getId());

        return toDto(customerSaved);
    }

    public CustomerResponse update(Long id, CustomerRequest request) {

        log.info(LogMessages.SEARCHING_CUSTOMER_FOR_UPDATE, id);

        Customer customer = findByCustomer(id);

        log.info(LogMessages.UPDATING_CUSTOMER, id);

        customer.setName(request.name());
        customer.setCpf(request.cpf());
        customer.setEmail(request.email());
        customer.setBirthDate(request.birth_date());
        customer.setUpdateAt(LocalDate.now());
        customer.setActive(request.active());

        Customer customerUpdated = customerRepository.save(customer);

        log.info(LogMessages.CUSTOMER_FOUND_FOR_UPDATE, id);

        return toDto(customerUpdated);
    }

    public List<CustomerResponse> findAll(){

        return  customerRepository.findAll().stream().map(this::toDto).sorted(Comparator.comparing(CustomerResponse::id)).toList();

    }

    public CustomerResponse findCustomerById(Long id){
        log.info(LogMessages.CUSTOMER_FOUND_BY_ID, id);

        Customer customer = findByCustomer(id);

        return toDto(customer);
    }

    @Transactional
    @Override
    public void deleteById(Long id){
        log.info(LogMessages.CUSTOMER_DELETE_BY_ID, id);

        Customer customer = findByCustomer(id);

        customerRepository.delete(customer);
        log.info(LogMessages.CUSTOMER_DELETED_BY_ID , id);


    }

    private Customer findByCustomer(Long id) {

        return customerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.CUSTOMER_NOT_FOUND + id));

    }

    private CustomerResponse toDto(Customer dto) {
        return new CustomerResponse(
                dto.getId(),
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getCreatedAt(),
                dto.getCpf(),
                dto.getBirthDate(),
                dto.isActive()
        );
    }
}
