package by.vadzimmatsiushonak.bank.api.controller;


import by.vadzimmatsiushonak.bank.api.mapper.BankPaymentMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankPaymentRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public BankPayment create(@RequestBody BankPaymentRequestDto bankPaymentRequestDto) {
        return bankPaymentService.create(bankPaymentMapper.toEntity(bankPaymentRequestDto));
    }
}
