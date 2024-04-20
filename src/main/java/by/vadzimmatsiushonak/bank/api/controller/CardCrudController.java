package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.CardMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CardRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.CardResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.CardRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.Card;
import by.vadzimmatsiushonak.bank.api.service.CardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Card", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/cards")
public class CardCrudController {

    private final CardService cardService;
    private final CardMapper cardMapper;

    @ApiOperation("Get Card by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> findById(@PathVariable String cardNumber) {
        return ResponseEntity.status(OK)
            .body(cardMapper.toResponse(cardService.findByCardNumber(cardNumber).orElse(null)));
    }

    @ApiOperation("Get Cards List")
    @ResponseStatus(OK)
    @GetMapping
    public ResponseEntity<List<CardResponse>> findAll() {
        return ResponseEntity.status(OK)
            .body(cardMapper.toListResponse(cardService.findAll()));
    }

    @ApiOperation("Get Card by id with card relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<CardRelationsResponse> findByIdWithRelations(@PathVariable String cardNumber) {
        return ResponseEntity.status(OK)
            .body(cardMapper.toResponseRelations(cardService.findByCardNumber(cardNumber).orElse(null)));
    }

    @ApiOperation("Get Cards List with card relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<CardRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
            .body(cardMapper.toListResponseRelations(cardService.findAll()));
    }

    @ApiOperation("Add Card to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<CardResponse> create(@Valid @RequestBody CardRequest request) {
        Card card = cardService.save(cardMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(cardMapper.toResponse(card));
    }

    @ApiOperation("Update all Card properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<CardResponse> update(@PathVariable String cardNumber,
        @Valid @RequestBody CardRequest request) {
        Card entity = cardMapper.toEntity(request);
        entity.setCardNumber(cardNumber);

        Card card = cardService.update(entity);
        return ResponseEntity.status(OK).body(cardMapper.toResponse(card));
    }

//    @ApiOperation("Update all Card properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<CardResponse> updateField(@Valid @RequestBody CardUpdateFieldRequestDto request) {
//        Card card = cardService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(cardMapper.toResponse(card));
//    }

}
