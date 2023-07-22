package by.vadzimmatsiushonak.bank.api.controller;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.OK;

import by.vadzimmatsiushonak.bank.api.mapper.UserMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.UserDtoRelations;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "User", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation("Get User with property relations")
    @GetMapping("/{id}")
    public ResponseEntity<UserDtoRelations> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
            .body(userMapper.toDtoRelations(userService.findById(id).orElse(null)));
    }

    @ApiOperation("Get List of Users with property relations")
    @GetMapping
    public ResponseEntity<List<UserDtoRelations>> findAll() {
        return ResponseEntity.status(OK)
            .body(userMapper.toListDtoRelations(userService.findAll()));
    }

}
