package by.vadzimmatsiushonak.bank.api.v2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static by.vadzimmatsiushonak.bank.api.constant.SwaggerConstant.EMPTY_DESCRIPTION;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@Api(tags = "Object", description = EMPTY_DESCRIPTION)
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/objects")
public class ObjectAdminController {

    private ObjectService objectService;
    private ObjectMapper objectMapper;

    @ApiOperation("Get Object by id")
    @ResponseStatus(OK)
    @GetMapping("/{id}")
    public ResponseEntity<ObjectResponse> findById(@PathVariable String id) {
        return ResponseEntity.status(OK)
                .body(objectMapper.toDto(objectService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Objects List")
    @ResponseStatus(OK)
    @GetMapping("/")
    public ResponseEntity<List<ObjectResponse>> findAll() {
        return ResponseEntity.status(OK)
                .body(objectMapper.toListDto(objectService.findAll()));
    }

    @ApiOperation("Get Object by id with object relations")
    @ResponseStatus(OK)
    @GetMapping("/{id}/relations")
    public ResponseEntity<ObjectRelationsResponse> findByIdWithRelations(@PathVariable String id) {
        return ResponseEntity.status(OK)
                .body(objectMapper.toDtoRelations(objectService.findById(id).orElse(null)));
    }

    @ApiOperation("Get Objects List with object relations")
    @ResponseStatus(OK)
    @GetMapping("/relations")
    public ResponseEntity<List<ObjectRelationsResponse>> findAllWithRelations() {
        return ResponseEntity.status(OK)
                .body(objectMapper.toListDtoRelations(objectService.findAll()));
    }

    @ApiOperation("Add Object to database")
    @ResponseStatus(CREATED)
    @PostMapping
    public ResponseEntity<ObjectResponse> create(@Valid @RequestBody ObjectRequest request) {
        Object object = objectService.save(objectMapper.toEntity(request));
        return ResponseEntity.status(CREATED).body(objectMapper.toDto(object));
    }

    @ApiOperation("Update all Object properties in database")
    @ResponseStatus(OK)
    @PutMapping("/{id}")
    public ResponseEntity<ObjectResponse> update(@PathVariable String id, @Valid @RequestBody ObjectRequest request) {
        Object entity = objectMapper.toEntity(request);
        entity.setId(id);

        Object object = objectService.update(entity);
        return ResponseEntity.status(OK).body(objectMapper.toDto(object));
    }

    @ApiOperation("Update all Object properties in database")
    @ResponseStatus(OK)
    @PatchMapping
    public ResponseEntity<ObjectResponse> updateField(@Valid @RequestBody ObjectUpdateFieldRequestDto request) {
        Object object = objectService.updateField(request.id, request.name, request.value);
        return ResponseEntity.status(OK).body(objectMapper.toDto(object));
    }

}
