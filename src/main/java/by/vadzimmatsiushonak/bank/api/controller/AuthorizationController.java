package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.mapper.UserMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.AuthRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.request.UserRequestDto;
import by.vadzimmatsiushonak.bank.api.model.dto.request.TokenRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.AuthResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.ConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.RegistrationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MAX_VALUE;
import static by.vadzimmatsiushonak.bank.api.util.NumberUtils.CONFIRMATION_MIN_VALUE;
import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_CREATED;
import static java.net.HttpURLConnection.HTTP_NOT_FOUND;
import static java.net.HttpURLConnection.HTTP_OK;
import static java.net.HttpURLConnection.HTTP_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Auth", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@Validated
@RestController
@RequestMapping("/oauth2/v1")
public class AuthorizationController {

    private final UserMapper userMapper;
    private final AuthorizationFacade authorizationFacade;

    /**
     * Perform authentication for active users, generate a confirmation code, send it to the
     * user, and return a unique identifier for confirm the token retrieval request.
     *
     * @param authRequest the user data to be confirmed to authenticate.
     * @return the auth response containing the UUID key for the token retrieval request
     */
    @ApiOperation("Authenticate a user account and returns a unique key for confirm the token retrieval request..")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "User authenticated successfully.", response = AuthResponse.class),
            @ApiResponse(code = HTTP_UNAUTHORIZED, message = "Invalid credentials provided."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found.")})
    @ResponseStatus(OK)
    @PostMapping("/auth")
    public ResponseEntity<AuthResponse> authenticate(
            @RequestBody @Validated AuthRequest authRequest) {
        String confirmationKey = authorizationFacade.authenticate(authRequest.username,
                authRequest.password);

        return ResponseEntity.status(OK).body(new AuthResponse(confirmationKey));
    }

    /**
     * Provide access token after successful key and code validation.
     *
     * @param tokenRequest the confirmation key and code to retrieve access token.
     * @return the token response containing the accessToken for secured endpoints
     */
    @ApiOperation("Provides access token using the confirmation key and code.")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "User authorized successful, token provided.", response = RegistrationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid confirmation code."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found.")})
    @ResponseStatus(OK)
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(
            @RequestBody @Validated TokenRequest tokenRequest) {
        String token = authorizationFacade.getToken(tokenRequest.key, tokenRequest.code);

        return ResponseEntity.status(OK).body(new TokenResponse(token));
    }

    /**
     * Revokes the provided token from the token store
     *
     * @param token the token authorization string
     * @return the Boolean response indicates successful token revocation ('true') or token not found ('false')
     */
    @ApiOperation("Revokes the provided token from the token store.")
    @ApiResponses({
            @ApiResponse(code = HTTP_OK, message = "The Boolean response indicates successful token revocation ('true') or token not found ('false').", response = Boolean.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid token code.")})
    @ResponseStatus(OK)
    @PostMapping("/revoke")
    public ResponseEntity<Boolean> revokeToken(@RequestBody @NotBlank String token) {
        boolean isSuccess = authorizationFacade.revokeToken(token);
        return ResponseEntity.status(OK).body(isSuccess);
    }

    /**
     * Register a new user account and a corresponding user account, generate a confirmation code, send it to the
     * user, and return a unique identifier for confirm the user account registration.
     *
     * @param userRequestDto the user data to be registered.
     * @return the registration response containing the UUID key for the user account confirmation.
     */
    @ApiOperation("Registers a new user account and returns a unique key for confirm the user account registration.")
    @ApiResponses({
            @ApiResponse(code = HTTP_CREATED, message = "User registered successfully.", response = RegistrationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid arguments provided.")})
    @ResponseStatus(CREATED)
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(
            @RequestBody @Validated UserRequestDto userRequestDto) {
        String confirmationKey = authorizationFacade.register(userMapper.toEntity(userRequestDto));
        return ResponseEntity.status(CREATED).body(new RegistrationResponse(confirmationKey));
    }

    /**
     * Confirm the registration of a user in the application using a confirmation key and code.
     *
     * @param key  the user key.
     * @param code the confirmation code.
     * @return the confirmation response indicating whether the confirmation was successful or not.
     */
    @ApiOperation("Confirm the registration of a user in the application using a confirmation key and code.")
    @ApiResponses(value = {
            @ApiResponse(code = HTTP_OK, message = "Confirmation successful.", response = ConfirmationResponse.class),
            @ApiResponse(code = HTTP_BAD_REQUEST, message = "Invalid confirmation code."),
            @ApiResponse(code = HTTP_NOT_FOUND, message = "User key not found."),})
    @PostMapping("/confirm/{key}/{code}")
    public ResponseEntity<ConfirmationResponse> confirmRegistration(
            @PathVariable @NotBlank String key,
            @PathVariable @Min(CONFIRMATION_MIN_VALUE) @Max(CONFIRMATION_MAX_VALUE) Integer code) {
        Boolean isConfirmed = authorizationFacade.confirmRegistration(key, code);
        return ResponseEntity.status(OK).body(new ConfirmationResponse(isConfirmed));
    }

}
