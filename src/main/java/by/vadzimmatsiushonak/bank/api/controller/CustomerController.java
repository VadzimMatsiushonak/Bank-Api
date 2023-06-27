package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CustomerDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CustomerDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Customer;
import by.vadzimmatsiushonak.bank.api.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Customer", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    @ApiOperation("Get Customer with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(customerMapper.toDtoRelations(customerService.findById(id).orElse(null)));
    }

    @ApiOperation("Get List of Customers with property relations")
    @GetMapping
    public ResponseEntity<List<CustomerDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(customerMapper.toListDtoRelations(customerService.findAll()));
    }

    @ApiOperation("Add the Customer to the Api database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<CustomerDto> create(@Valid @RequestBody CustomerRequestDto customerRequestDto) {
        Customer customer = customerService.save(customerMapper.toEntity(customerRequestDto));
        return ResponseEntity.status(CREATED).body(customerMapper.toDto(customer));
    }
}
