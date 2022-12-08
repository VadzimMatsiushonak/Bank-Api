package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;

import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "_Status", description = EMPTY_DESCRIPTION)
@RestController
public class StatusRestController {

    @GetMapping("/api/v1/status")
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("OK");
    }

}
