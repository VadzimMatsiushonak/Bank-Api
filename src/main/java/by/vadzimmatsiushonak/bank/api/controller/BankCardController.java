package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankCardMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankCardRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankCardDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankCard;
import by.vadzimmatsiushonak.bank.api.service.BankCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Card", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class BankCardController {

    private final BankCardService bankCardService;
    private final BankCardMapper bankCardMapper;

    @ApiOperation("Get Card with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<BankCardDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankCardMapper.toDtoRelations(bankCardService.findById(id).orElse(null)));
    }

    @ApiOperation("Get List of Cards with property relations")
    @GetMapping
    public ResponseEntity<List<BankCardDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankCardMapper.toListDtoRelations(bankCardService.findAll()));
    }

    @ApiOperation("Add the Card to the Api database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<BankCard> create(@RequestBody BankCardRequestDto bankCardRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankCardService.create(bankCardMapper.toEntity(bankCardRequestDto)));
    }
}
