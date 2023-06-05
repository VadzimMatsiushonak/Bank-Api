package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.LoginRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.TokenRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.response.ConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.LoginResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.RegistrationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TokenResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.VERIFICATION_MIN_VALUE;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Auth", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/oauth2/v1")
public class AuthorizationController {

    private final CustomerMapper customerMapper;
    private final AuthorizationFacade authorizationFacade;

    @ResponseStatus(OK)
    @PostMapping("/auth")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody @Validated LoginRequestDto loginRequestDto) {
        String confirmationKey = authorizationFacade.authenticate(loginRequestDto.username,
                loginRequestDto.password);
        return ResponseEntity.status(OK).body(new LoginResponse(confirmationKey));
    }

    @ResponseStatus(OK)
    @PostMapping("/token")
    public ResponseEntity<TokenResponseDto> token(
            @RequestBody @Validated TokenRequestDto tokenRequestDto) {
        String token = authorizationFacade.getToken(tokenRequestDto.key, tokenRequestDto.code);

        return ResponseEntity.status(OK).body(new TokenResponseDto(token));
    }

    /**
     * Registers a new customer account and a corresponding user account, generates a confirmation code, sends it to the
     * user, and returns a unique identifier for confirming the user account registration.
     *
     * @param customerDto the customer data to be registered.
     * @return the registration response containing the UUID key for the user account confirmation.
     */
    @ApiOperation("Registers a new customer account and returns a unique key for confirming the user account registration.")
    @ApiResponses({
            @ApiResponse(code = HTTP_CREATED, message = "Customer registered successfully.", response = RegistrationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided.")})
    @ResponseStatus(CREATED)
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerCustomer(
            @RequestBody @Validated CustomerRequestDto customerDto) {
        String confirmationKey = authorizationFacade.register(customerMapper.toEntity(customerDto));
        return ResponseEntity.status(CREATED).body(new RegistrationResponse(confirmationKey));
    }

    /**
     * Confirms the registration of a customer in the application using a confirmation key and code.
     *
     * @param key  the user key.
     * @param code the confirmation code.
     * @return the confirmation response indicating whether the verification was successful or not.
     */
    @ApiOperation("Confirms the registration of a customer in the application using a confirmation key and code.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Confirmation successful.", response = ConfirmationResponse.class),
            @ApiResponse(code = 400, message = "Invalid confirmation code."),
            @ApiResponse(code = 404, message = "User key not found."),})
    @PostMapping("/confirm/{key}/{code}")
    public ResponseEntity<ConfirmationResponse> confirmCustomer(@PathVariable @NotBlank String key,
                                                                @PathVariable @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        Boolean isConfirmed = authorizationFacade.confirmRegistration(key, code);
        return ResponseEntity.status(OK).body(new ConfirmationResponse(isConfirmed));
    }

}
