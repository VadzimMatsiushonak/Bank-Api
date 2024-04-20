package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.AccountMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.AccountRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.AccountRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Account;
import by.vadzimmatsiushonak.bank.api.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Account", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accounts")
public class AccountCrudController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;

    @ApiOperation("Get Account by iban")
    @ResponseStatus(OK)
    @GetMapping("/{iban}")
    public ResponseEntity<AccountResponse> findByIban(@PathVariable String iban) {
        return ResponseEntity.status(OK)
            .body(accountMapper.toResponse(accountService.findByIban(iban).orElse(null)));
    }

    @ApiOperation("Get Accounts List")
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<AccountResponse>> findAll() {
        return ResponseEntity.status(OK)
            .body(accountMapper.toListResponse(accountService.findAll()));
    }

    @ApiOperation("Get Account by id with account relations")
    @ResponseStatus(OK)
    @GetMapping("/{iban}/relations")
    public ResponseEntity<AccountRelationsResponse> findByIbanWithRelations(@PathVariable String iban) {
        return ResponseEntity.status(OK)
            .body(accountMapper.toResponseRelations(accountService.findByIban(iban).orElse(null)));
    }

    @ApiOperation("Get Accounts List with account relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<AccountRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
            .body(accountMapper.toListResponseRelations(accountService.findAll()));
    }

    @ApiOperation("Add Account to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<AccountResponse> create(@Valid @RequestBody AccountRequest request) {
        Account account = accountService.save(accountMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(accountMapper.toResponse(account));
    }

    @ApiOperation("Update all Account properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{iban}")
    public ResponseEntity<AccountResponse> update(@PathVariable String iban,
        @Valid @RequestBody AccountRequest request) {
        Account entity = accountMapper.toEntity(request);
        entity.setIban(iban);

        Account account = accountService.update(entity);
        return ResponseEntity.status(OK).body(accountMapper.toResponse(account));
    }

//    @ApiOperation("Update all Account properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<AccountResponse> updateField(@Valid @RequestBody AccountUpdateFieldRequestDto request) {
//        Account account = accountService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(accountMapper.toResponse(account));
//    }

}
