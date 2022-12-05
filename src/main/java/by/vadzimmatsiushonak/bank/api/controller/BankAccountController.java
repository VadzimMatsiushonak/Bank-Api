package by.vadzimmatsiushonak.bank.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankAccountMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankAccountRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
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
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BankAccountDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankAccountMapper.toDtoRelations(bankAccountService.findById(id).orElse(null)));
    }

    @GetMapping
    public ResponseEntity<List<BankAccountDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankAccountMapper.toListDtoRelations(bankAccountService.findAll()));
    }

    @PostMapping
    public ResponseEntity<BankAccount> create(@RequestBody BankAccountRequestDto bankAccountRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankAccountService.create(bankAccountMapper.toEntity(bankAccountRequestDto)));
    }
}
