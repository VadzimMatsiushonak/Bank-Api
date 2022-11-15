package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankPaymentMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/bankPayments")
public class BankPaymentController {

    private final BankPaymentService bankPaymentService;
    private final BankPaymentMapper bankPaymentMapper;

    @GetMapping("/{id}")
    public BankPayment findById(@PathVariable Long id) {
        return bankPaymentService.findById(id).orElse(null);
    }

    @GetMapping
    public List<BankPaymentDtoRelations> findAll() {
        return bankPaymentMapper.toListDtoRelations(bankPaymentService.findAll());
    }
}
