package by.vadzimmatsiushonak.bank.api.controller;


import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankCardMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankCardRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankCardDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import by.vadzimmatsiushonak.bank.api.service.BankCardService;
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
@RequestMapping("/api/bankCards")
public class BankCardController {

    private final BankCardService bankCardService;
    private final BankCardMapper bankCardMapper;

    @GetMapping("/{id}")
    public ResponseEntity<BankCardDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankCardMapper.toDtoRelations(bankCardService.findById(id).orElse(null)));
    }

    @GetMapping
    public ResponseEntity<List<BankCardDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankCardMapper.toListDtoRelations(bankCardService.findAll()));
    }

    @PostMapping
    public ResponseEntity<BankCard> create(@RequestBody BankCardRequestDto bankCardRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankCardService.create(bankCardMapper.toEntity(bankCardRequestDto)));
    }
}
