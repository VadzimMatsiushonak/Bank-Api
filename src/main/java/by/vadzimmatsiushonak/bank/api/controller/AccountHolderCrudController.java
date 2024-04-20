package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.SecurityUtils.getAuthLogin;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.AccountHolderMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.AccountHolderRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AccountHolderResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.AccountHolderRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.AccountHolder;
import by.vadzimmatsiushonak.bank.api.service.AccountHolderService;
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

@Api(tags = "AccountHolder", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/accountHolders")
public class AccountHolderCrudController {

    private final AccountHolderService accountHolderService;
    private final AccountHolderMapper accountHolderMapper;

    @ApiOperation("Get AccountHolder by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<AccountHolderResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(accountHolderMapper.toResponse(accountHolderService.findById(id).orElse(null)));
    }

    @ApiOperation("Get AccountHolders List")
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<AccountHolderResponse>> findAll() {
        return ResponseEntity.status(OK)
            .body(accountHolderMapper.toListResponse(accountHolderService.findAll()));
    }

    @ApiOperation("Get AccountHolder by id with accountHolder relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<AccountHolderRelationsResponse> findByIdWithRelations(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(accountHolderMapper.toResponseRelations(accountHolderService.findById(id).orElse(null)));
    }

    @ApiOperation("Get AccountHolders List with accountHolder relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<AccountHolderRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
            .body(accountHolderMapper.toListResponseRelations(accountHolderService.findAll()));
    }

    @ApiOperation("Add AccountHolder to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<AccountHolderResponse> create(@Valid @RequestBody AccountHolderRequest request) {
        AccountHolder accountHolder = accountHolderService.save(accountHolderMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(accountHolderMapper.toResponse(accountHolder));
    }

    @ApiOperation("Update all AccountHolder properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<AccountHolderResponse> update(@PathVariable Long id,
        @Valid @RequestBody AccountHolderRequest request) {
        AccountHolder entity = accountHolderMapper.toEntity(request);
        entity.setId(id);

        AccountHolder accountHolder = accountHolderService.update(entity);
        return ResponseEntity.status(OK).body(accountHolderMapper.toResponse(accountHolder));
    }

    @ApiOperation("Get current account holder information")
    @ResponseStatus(OK)
    @GetMapping("/info")
    public ResponseEntity<AccountHolderRelationsResponse> info() {
        return ResponseEntity.status(OK)
            .body(accountHolderMapper.toResponseRelations(
                accountHolderService.findByUserLogin(getAuthLogin()).orElse(null)));
    }

//    @ApiOperation("Update all AccountHolder properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<AccountHolderResponse> updateField(@Valid @RequestBody AccountHolderUpdateFieldRequestDto request) {
//        AccountHolder accountHolder = accountHolderService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(accountHolderMapper.toResponse(accountHolder));
//    }

}
