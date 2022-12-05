package by.vadzimmatsiushonak.bank.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(customerMapper.toDtoRelations(customerService.findById(id).orElse(null)));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(customerMapper.toListDtoRelations(customerService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Customer> create(@RequestBody CustomerRequestDto customerRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(customerService.create(customerMapper.toEntity(customerRequestDto)));
    }
}
