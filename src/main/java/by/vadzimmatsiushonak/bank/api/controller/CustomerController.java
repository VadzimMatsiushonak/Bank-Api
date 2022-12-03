package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import java.util.List;
import lombok.AllArgsConstructor;
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
    public CustomerDtoRelations findById(@PathVariable Long id) {
        return customerMapper.toDtoRelations(customerService.findById(id).orElse(null));
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
