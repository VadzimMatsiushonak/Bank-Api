package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankCardMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankCardDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import by.vadzimmatsiushonak.bank.api.service.BankCardService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bankCards")
public class BankCardController {

    private final BankCardService bankCardService;
    private final BankCardMapper bankCardMapper;

    @GetMapping("/{id}")
    public BankCard findById(@PathVariable Long id) {
        return bankCardService.findById(id).orElse(null);
    }

    @GetMapping
    public List<BankCardDtoRelations> findAll() {
        return bankCardMapper.toListDtoRelations(bankCardService.findAll());
    }
}
