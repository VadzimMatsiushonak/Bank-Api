package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.Bank;
import by.vadzimmatsiushonak.bank.api.service.BankService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;
    private final BankMapper bankMapper;

    @GetMapping("/{id}")
    public Bank findById(@PathVariable Long id) {
        return bankService.findById(id).orElse(null);
    }

    @GetMapping
    public List<BankDtoRelations> findAll() {
        return bankMapper.toListDtoRelations(bankService.findAll());
    }
}
