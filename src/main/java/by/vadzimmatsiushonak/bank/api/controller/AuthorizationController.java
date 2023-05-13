package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.facade.AuthorizationFacade;
import by.vadzimmatsiushonak.bank.api.mapper.CustomerMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.CustomerRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "Auth", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {

    private final CustomerMapper customerMapper;

    private final AuthorizationFacade authorizationFacade;

    @ApiOperation("Creates a new customer account and user account, generates a confirmation code, sends it to the user, and returns a UUID key for the user account confirmation.")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Validated CustomerRequestDto customerDto) {
        String confirmationKey = authorizationFacade.registerCustomer(customerMapper.toEntity(customerDto));
        return ResponseEntity.status(CREATED).body(confirmationKey);
    }


    @ApiOperation("Confirm customer registration in out application")
    @PostMapping("/confirm/{key}/{code}")
    public ResponseEntity<Boolean> confirm(@PathVariable @NotBlank String key, @PathVariable @NotBlank String code) {
        Boolean result = authorizationFacade.confirmCustomer(key, code);
        return ResponseEntity.status(OK).body(result);
    }

}
