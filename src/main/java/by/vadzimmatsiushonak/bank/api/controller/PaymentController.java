package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.SecurityUtils.getAuthLogin;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.facade.PaymentFacade;
import by.vadzimmatsiushonak.bank.api.model.dto.request.InitiateTransactionRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.ConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.InitiatedTransactionResponse;
import by.vadzimmatsiushonak.bank.api.model.pojo.TransactionConfirmation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.util.Collections;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Payment", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @ApiOperation("Initiates a payment between 2 accounts")
    @ApiResponses({
        @ApiResponse(code = HTTP_CREATED, message = "Provides created Payment from database"),
        @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided")})
    @ResponseStatus(CREATED)
    @PostMapping("/initiatePayment") // TODO maybe it's better to call in transfer instead of payment
    public ResponseEntity<InitiatedTransactionResponse> initiatePayment(
        @Valid @RequestBody InitiateTransactionRequest initiateTransactionRequest) {
        TransactionConfirmation confirmation = paymentFacade.initiatePayment(getAuthLogin(),
            initiateTransactionRequest);
        return ResponseEntity.status(CREATED)
            .body(new InitiatedTransactionResponse(confirmation.key, confirmation.transactionId));
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
        Long transactionId = paymentFacade.confirmPayment(key, code);
        return ResponseEntity.status(OK)
            .body(new ConfirmationResponse(true, Collections.singletonMap("transaction_id", transactionId)));
    }

}
