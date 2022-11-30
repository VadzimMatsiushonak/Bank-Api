package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @GetMapping("/{id}")
    public Customer findById(@PathVariable Long id) {
        return customerService.findById(id).orElse(null);
    }

    @GetMapping
    public List<CustomerDtoRelations> findAll() {
        return customerMapper.toListDtoRelations(customerService.findAll());
    }

    @PostMapping
    public Customer create(@RequestBody CustomerRequestDto customerRequestDto) {
        return customerService.create(customerMapper.toEntity(customerRequestDto));
    }
}
