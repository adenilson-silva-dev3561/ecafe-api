package eCafe.API.customers.controller;

import eCafe.API.common.constants.ApiRoutes;
import eCafe.API.customers.dto.CustomerRequest;
import eCafe.API.customers.dto.CustomerResponse;
import eCafe.API.customers.dto.CustomerUpdateRequest;
import eCafe.API.customers.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(ApiRoutes.CUSTOMER)
@Tag(name = "Customers", description = "Gerenciamento de clientes")
public class CustomerController {

    private final CustomerService customerService;


    @Operation(summary = "Cadastrar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso!"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos!"),
            @ApiResponse(responseCode = "409", description = "Cpf já cadastrado."),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest dto) {

        CustomerResponse customer = customerService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(customer);
    }

    @Operation(summary = "Atualizar cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso."),
            @ApiResponse(responseCode = "400", description = "Dados inválidos!"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")

    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> update(@PathVariable Long id, @Valid @RequestBody CustomerUpdateRequest request) {

        CustomerResponse customerUpdated = customerService.update(id, request);

        return ResponseEntity.status(HttpStatus.OK).body(customerUpdated);
    }

    @Operation(summary = "Buscar todos clientes")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso!"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findAll(){

        List<CustomerResponse> customers = customerService.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @Operation(summary = "Buscar cliente por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Cliente com id informado não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> findCustomerById(@Valid @PathVariable Long id) {
        CustomerResponse customerId = customerService.findCustomerById(id);

        return ResponseEntity.ok(customerId);
    }

    @Operation(summary = "Buscar cliente por id.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente deletado com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Cliente com id informado não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@Valid @PathVariable Long id){

       customerService.deleteById(id);
       return ResponseEntity.noContent().build();
    }
    @GetMapping("/me")
    public ResponseEntity<CustomerResponse>customerMe(Authentication authentication){

        CustomerResponse customer = customerService.findCurrentCustomer(authentication);

        return ResponseEntity.ok(customer);
    }

}
