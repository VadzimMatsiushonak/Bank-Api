package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.model.dto.request.v2.AuthRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.request.v2.RegistrationRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.request.v2.TokenRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.v2.ConfirmationResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.v2.TokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Auth", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/oauth")
public class AuthController {

    @ApiOperation("Register new user, return confirmations data")
    @ResponseStatus(OK)
    @PostMapping("/register")
    public ResponseEntity<ConfirmationResponse> register(@RequestBody RegistrationRequest request) {
        // TODO: new implementation
        return ResponseEntity.status(OK).body(null);
    }

    @ApiOperation("Authenticate user with credentials, return confirmations data")
    @ResponseStatus(OK)
    @PostMapping("/auth")
    public ResponseEntity<ConfirmationResponse> auth(@RequestBody AuthRequest request) {
        // TODO: new implementation
        return ResponseEntity.status(OK).body(null);
    }

    @ApiOperation("Give access_token for provided confirmations data")
    @ResponseStatus(OK)
    @PostMapping("/token")
    public ResponseEntity<TokenResponse> token(@RequestBody TokenRequest request) {
        // TODO: new implementation
        return ResponseEntity.status(OK).body(null);
    }

}
