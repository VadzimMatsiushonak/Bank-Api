package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.service.BankService;
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

@Api(tags = "Bank", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/banks")
public class BankController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @ApiOperation("Get Bank with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<BankDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankMapper.toDtoRelations(bankService.findById(id).orElse(null)));
    }

    @ApiOperation("Get List of Banks with property relations")
    @GetMapping
    public ResponseEntity<List<BankDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankMapper.toListDtoRelations(bankService.findAll()));
    }

    @ApiOperation("Add the Bank to the Api database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<BankDto> create(@Valid @RequestBody BankRequestDto bankRequestDto) {
        Bank bank = bankService.create(bankMapper.toEntity(bankRequestDto));
        return ResponseEntity.status(CREATED).body(bankMapper.toDto(bank));
    }
}
