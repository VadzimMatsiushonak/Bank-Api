package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.mapper.BankPaymentMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.BankPaymentRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiatePaymentRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.BankPaymentDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.ConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.BankPaymentDtoRelations;
import by.vadzimmatsiushonak.bank.api.model.entity.BankPayment;
import by.vadzimmatsiushonak.bank.api.service.BankPaymentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.SecurityUtils.getCurrentUserPhoneNumber;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Payment", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class BankPaymentController {

    private final BankPaymentService bankPaymentService;
    private final BankPaymentMapper bankPaymentMapper;

    private final PaymentFacade paymentFacade;

    @ApiOperation("Get Payment with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<BankPaymentDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(bankPaymentMapper.toDtoRelations(
                        bankPaymentService.findById(id).orElse(null)));
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
    public ResponseEntity<BankPaymentDto> create(
            @Valid @RequestBody BankPaymentRequestDto bankPaymentRequestDto) {
        BankPayment bankPayment = bankPaymentService.save(
                bankPaymentMapper.toEntity(bankPaymentRequestDto));
        return ResponseEntity.status(CREATED).body(bankPaymentMapper.toDto(bankPayment));
    }

    @ApiOperation("Initiates a payment between 2 accounts")
    @ApiResponses({
            @ApiResponse(code = HTTP_CREATED, message = "Provides created Payment from database"),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided")})
    @ResponseStatus(CREATED)
    @PostMapping("/initiatePayment")
    public ResponseEntity<BankPaymentConfirmationResponse> initiatePayment(
            @Valid @RequestBody InitiatePaymentRequest initiatePaymentRequest) {
        String confirmationKey = paymentFacade.initiatePayment(getCurrentUserPhoneNumber(),
                initiatePaymentRequest);
        return ResponseEntity.status(CREATED).body(new BankPaymentConfirmationResponse(confirmationKey));
    }

    @ApiOperation("Confirm the user's payment")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Confirmation payment successful.", response = ConfirmationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid confirmation code."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found.")})
    @PostMapping("/confirmPayment/{key}/{code}")
    public ResponseEntity<ConfirmationResponse> confirmPayment(
            @PathVariable @NotBlank String key,
            @PathVariable @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Boolean isConfirmed = paymentFacade.confirmPayment(key, code);
        return ResponseEntity.status(OK).body(new ConfirmationResponse(isConfirmed));
    }

}
