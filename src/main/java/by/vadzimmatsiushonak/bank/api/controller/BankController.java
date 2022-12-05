package by.vadzimmatsiushonak.bank.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.service.BankService;
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
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BankDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankMapper.toDtoRelations(bankService.findById(id).orElse(null)));
    }

    @GetMapping
    public ResponseEntity<List<BankDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankMapper.toListDtoRelations(bankService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Bank> create(@RequestBody BankRequestDto bankRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankService.create(bankMapper.toEntity(bankRequestDto)));
    }
}
