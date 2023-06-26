package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankAccountMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankAccountRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankAccountDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
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

@Api(tags = "Account", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @ApiOperation("Get Account with property relations")
    @GetMapping("/{iban}")
    public ResponseEntity<BankAccountDtoRelations> findById(@PathVariable String iban) {
        return ResponseEntity.status(OK)
            .body(bankAccountMapper.toDtoRelations(bankAccountService.findById(iban).orElse(null)));
    }

    @ApiOperation("Get List of Accounts with property relations")
    @GetMapping
    public ResponseEntity<List<BankAccountDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankAccountMapper.toListDtoRelations(bankAccountService.findAll()));
    }

    @ApiOperation("Add the Account to the Api database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<BankAccountDto> create(@Valid @RequestBody BankAccountRequestDto bankAccountRequestDto) {
        BankAccount bankAccount = bankAccountService.create(
                bankAccountMapper.toEntity(bankAccountRequestDto));
        return ResponseEntity.status(CREATED).body(bankAccountMapper.toDto(bankAccount));
    }
}
