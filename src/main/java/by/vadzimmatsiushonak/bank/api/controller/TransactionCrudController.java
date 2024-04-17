package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.mapper.TransactionMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.TransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TransactionResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.TransactionRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Transaction;
import by.vadzimmatsiushonak.bank.api.service.TransactionService;
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

@Api(tags = "Transaction", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionCrudController {

    private final TransactionService transactionService;
    private final TransactionMapper transactionMapper;

    @ApiOperation("Get Transaction by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(transactionMapper.toResponse(transactionService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Transactions List")
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<TransactionResponse>> findAll() {
        return ResponseEntity.status(OK)
                .body(transactionMapper.toListResponse(transactionService.findAll()));
    }

    @ApiOperation("Get Transaction by id with transaction relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<TransactionRelationsResponse> findByIdWithRelations(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(transactionMapper.toResponseRelations(transactionService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Transactions List with transaction relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<TransactionRelationsResponse>> findAllWithRelations() {
        List<Transaction> all = transactionService.findAll();
        List<TransactionRelationsResponse> listResponseRelations = transactionMapper.toListResponseRelations(all);
        return ResponseEntity.status(OK)
                .body(listResponseRelations);
    }

    @ApiOperation("Add Transaction to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<TransactionResponse> create(@Valid @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.save(transactionMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(transactionMapper.toResponse(transaction));
    }

    @ApiOperation("Update all Transaction properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse> update(@PathVariable Long id, @Valid @RequestBody TransactionRequest request) {
        Transaction entity = transactionMapper.toEntity(request);
        entity.setId(id);

        Transaction transaction = transactionService.update(entity);
        return ResponseEntity.status(OK).body(transactionMapper.toResponse(transaction));
    }

//    @ApiOperation("Update all Transaction properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<TransactionResponse> updateField(@Valid @RequestBody TransactionUpdateFieldRequestDto request) {
//        Transaction transaction = transactionService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(transactionMapper.toResponse(transaction));
//    }

}
