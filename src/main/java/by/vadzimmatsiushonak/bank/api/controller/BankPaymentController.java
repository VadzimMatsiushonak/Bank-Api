package by.vadzimmatsiushonak.bank.api.controller;


import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.BankPaymentMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankPaymentRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.List;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Payment", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class BankPaymentController {

    private final BankPaymentService bankPaymentService;
    private final BankPaymentMapper bankPaymentMapper;

    @ApiOperation("Get Payment with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<BankPaymentDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(bankPaymentMapper.toDtoRelations(bankPaymentService.findById(id).orElse(null)));
    }

    @ApiOperation("Get List of Payments with property relations")
    @GetMapping
    public ResponseEntity<List<BankPaymentDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(bankPaymentMapper.toListDtoRelations(bankPaymentService.findAll()));
    }

    @ApiOperation("Add the Payment to the Api database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<BankPayment> create(@RequestBody BankPaymentRequestDto bankPaymentRequestDto) {
        return ResponseEntity.status(CREATED)
            .body(bankPaymentService.create(bankPaymentMapper.toEntity(bankPaymentRequestDto)));
    }

    @ApiOperation("Initiates a payment between 2 accounts")
    @ApiResponses({@ApiResponse(code = HTTP_CREATED, message = "Provides created Payment from database"),
        @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided")})
    @ResponseStatus(CREATED)
    @PostMapping("/initiatePayment")
    public ResponseEntity<BankPayment> initiatePayment(
        @Valid @RequestBody InitiatePaymentRequest initiatePaymentRequest) {
        return ResponseEntity.status(CREATED).body(bankPaymentService.initiatePayment(initiatePaymentRequest));
    }
}
