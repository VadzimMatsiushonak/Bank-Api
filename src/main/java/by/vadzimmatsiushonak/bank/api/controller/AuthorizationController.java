package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.AuthRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.request.TokenRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.VerificationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AuthResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.RegistrationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TokenResponse;
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
import static java.net.HttpURLConnection.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Auth", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/oauth2/v1")
public class AuthorizationController {

    private final CustomerMapper customerMapper;
    private final AuthorizationFacade authorizationFacade;


    /**
     * Perform authentication for active users, generates a verification code, sends it to the
     * user, and returns a unique identifier for confirming the token retrieval request.
     *
     * @param authRequest the user data to be verified to authenticate.
     * @return the auth response containing the UUID key for the token retrieval request
     */
    @ApiOperation("Authenticate a user account and returns a unique key for confirming the token retrieval request..")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "User authenticated successfully.", response = AuthResponse.class),
            @ApiResponse(code = HTTP_UNAUTHORIZED, message = "Invalid credentials provided."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found.")})
    @ResponseStatus(OK)
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Validated AuthRequest authRequest) {
        String verificationKey = authorizationFacade.authenticate(authRequest.username,
                authRequest.password);

        return ResponseEntity.status(OK).body(new AuthResponse(verificationKey));
    }


    /**.
     * Provides access token after successful key and code validation.
     *
     * @param tokenRequest the verification key and code to retrieve access token.
     * @return the token response containing the accessToken for secured endpoints
     */
    @ApiOperation("Provides access token using the verification key and code.")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "User authorized successful, token provided.", response = RegistrationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid verification code."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found.")})
    @ResponseStatus(OK)
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(
            @RequestBody @Validated TokenRequest tokenRequest) {
        String token = authorizationFacade.getToken(tokenRequest.key, tokenRequest.code);

        return ResponseEntity.status(OK).body(new TokenResponse(token));
    }

    /**
     * Registers a new customer account and a corresponding user account, generates a verification code, sends it to the
     * user, and returns a unique identifier for confirming the user account registration.
     *
     * @param customerDto the customer data to be registered.
     * @return the registration response containing the UUID key for the user account verification.
     */
    @ApiOperation("Registers a new customer account and returns a unique key for confirming the user account registration.")
    @ApiResponses({
            @ApiResponse(code = HTTP_CREATED, message = "Customer registered successfully.", response = RegistrationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided.")})
    @ResponseStatus(CREATED)
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerCustomer(
            @RequestBody @Validated CustomerRequestDto customerDto) {
        String verificationKey = authorizationFacade.register(customerMapper.toEntity(customerDto));
        return ResponseEntity.status(CREATED).body(new RegistrationResponse(verificationKey));
    }

    /**This endpoint is used to confirm the user's token retrieval request by providing the verification key received from the /authenticate endpoint. Upon successful verification, an access token is returned, which can be used to access protected resources in the application.
     * Confirms the registration of a customer in the application using a verification key and code.
     *
     * @param key  the user key.
     * @param code the verification code.
     * @return the verification response indicating whether the verification was successful or not.
     */
    @ApiOperation("Confirms the registration of a customer in the application using a verification key and code.")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Verification successful.", response = VerificationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid verification code."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found."),})
    @PostMapping("/verify/{key}/{code}")
    public ResponseEntity<VerificationResponse> verifyRegistration(@PathVariable @NotBlank String key,
                                                                   @PathVariable @Min(VERIFICATION_MIN_VALUE) @Max(VERIFICATION_MAX_VALUE) Integer code) {
        Boolean isConfirmed = authorizationFacade.verifyRegistration(key, code);
        return ResponseEntity.status(OK).body(new VerificationResponse(isConfirmed));
    }

}
