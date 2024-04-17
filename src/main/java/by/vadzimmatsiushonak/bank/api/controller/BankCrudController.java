package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.mapper.BankMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.service.BankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Bank", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/banks")
public class BankCrudController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @ApiOperation("Get Bank by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<BankResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(bankMapper.toResponse(bankService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Banks List")
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<BankResponse>> findAll() {
        return ResponseEntity.status(OK)
                .body(bankMapper.toListResponse(bankService.findAll()));
    }

    @ApiOperation("Get Bank by id with bank relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<BankRelationsResponse> findByIdWithRelations(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(bankMapper.toResponseRelations(bankService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Banks List with bank relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<BankRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
                .body(bankMapper.toListResponseRelations(bankService.findAll()));
    }

    @ApiOperation("Add Bank to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<BankResponse> create(@Valid @RequestBody BankRequest request) {
        Bank bank = bankService.save(bankMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(bankMapper.toResponse(bank));
    }

    @ApiOperation("Update all Bank properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<BankResponse> update(@PathVariable Long id, @Valid @RequestBody BankRequest request) {
        Bank entity = bankMapper.toEntity(request);
        entity.setId(id);

        Bank bank = bankService.update(entity);
        return ResponseEntity.status(OK).body(bankMapper.toResponse(bank));
    }

//    @ApiOperation("Update all Bank properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<BankResponse> updateField(@Valid @RequestBody BankUpdateFieldRequestDto request) {
//        Bank bank = bankService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(bankMapper.toResponse(bank));
//    }

}
