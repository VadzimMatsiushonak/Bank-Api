package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankAccountMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankAccountDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankAccount;
import by.vadzimmatsiushonak.bank.api.service.BankAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bankAccount")
public class BankAccountController {

    private final BankAccountService bankAccountService;
    private final BankAccountMapper bankAccountMapper;

    @GetMapping("/{id}")
    public BankAccount findById(@PathVariable Long id) {
        return bankAccountService.findById(id).orElse(null);
    }

    @GetMapping
    public List<BankAccountDtoRelations> findAll() {
        return bankAccountMapper.toListDtoRelations(bankAccountService.findAll());
    }
}
