package by.vadzimmatsiushonak.bank.api.controller;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankPaymentMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankPaymentRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import java.util.List;
import javax.validation.Valid;
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
@RequestMapping("/api/bankPayments")
public class BankPaymentController {

    private final BankPaymentService bankPaymentService;
    private final BankPaymentMapper bankPaymentMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BankPaymentDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankPaymentMapper.toDtoRelations(bankPaymentService.findById(id).orElse(null)));
    }

    @GetMapping
    public ResponseEntity<List<BankPaymentDtoRelations>> findAll() {
        return ResponseEntity.status(OK).body(bankPaymentMapper.toListDtoRelations(bankPaymentService.findAll()));
    }

    @PostMapping
    public ResponseEntity<BankPayment> create(@RequestBody BankPaymentRequestDto bankPaymentRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankPaymentService.create(bankPaymentMapper.toEntity(bankPaymentRequestDto)));
    }

    @PostMapping("/initiatePayment")
    public ResponseEntity<?> initiatePayment(@Valid @RequestBody InitiatePaymentRequest initiatePaymentRequest) {
        try {
            return ResponseEntity.status(CREATED).body(bankPaymentService.initiatePayment(initiatePaymentRequest));
        } catch (RuntimeException e) {
            return ResponseEntity.status(BAD_REQUEST).body(e.getMessage());
        }
    }
}
