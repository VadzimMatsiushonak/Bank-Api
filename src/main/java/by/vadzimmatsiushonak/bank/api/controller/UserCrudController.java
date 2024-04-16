package by.vadzimmatsiushonak.bank.api.controller;

import by.vadzimmatsiushonak.bank.api.mapper.UserMapper;
import by.vadzimmatsiushonak.bank.api.model.dto.request.UserRequest;
import by.vadzimmatsiushonak.bank.api.model.dto.response.UserResponse;
import by.vadzimmatsiushonak.bank.api.model.dto.response.relations.UserRelationsResponse;
import by.vadzimmatsiushonak.bank.api.model.entity.User;
import by.vadzimmatsiushonak.bank.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static by.vadzimmatsiushonak.bank.api.util.SecurityUtils.getAuthLogin;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "User", description = EMPTY_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserCrudController {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation("Get User by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(userMapper.toResponse(userService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Users List")
    @ResponseStatus(OK)
    @GetMapping("/")
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.status(OK)
                .body(userMapper.toListResponse(userService.findAll()));
    }

    @ApiOperation("Get User by id with user relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<UserRelationsResponse> findByIdWithRelations(@PathVariable Long id) {
        return ResponseEntity.status(OK)
                .body(userMapper.toResponseRelations(userService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Users List with user relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<UserRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
                .body(userMapper.toListResponseRelations(userService.findAll()));
    }

    @ApiOperation("Add User to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody UserRequest request) {
        User user = userService.save(userMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(userMapper.toResponse(user));
    }

    @ApiOperation("Update all User properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        User entity = userMapper.toEntity(request);
        entity.setId(id);

        User user = userService.update(entity);
        return ResponseEntity.status(OK).body(userMapper.toResponse(user));
    }

    @ApiOperation("Get current user information")
    @ResponseStatus(OK)
    @GetMapping("/info")
    public ResponseEntity<UserResponse> info() {
        return ResponseEntity.status(OK)
                .body(userMapper.toResponse(userService.findByLogin(getAuthLogin()).orElse(null)));
    }

//    @ApiOperation("Update all User properties in database")
//    @ResponseStatus(OK)
//    @PatchMapping
//    public ResponseEntity<UserResponse> updateField(@Valid @RequestBody UserUpdateFieldRequestDto request) {
//        User user = userService.updateField(request.id, request.name, request.value);
//        return ResponseEntity.status(OK).body(userMapper.toResponse(user));
//    }

}
