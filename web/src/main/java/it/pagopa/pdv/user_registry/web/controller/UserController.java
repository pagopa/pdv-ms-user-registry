package it.pagopa.pdv.user_registry.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import it.pagopa.pdv.user_registry.core.UserService;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;
import it.pagopa.pdv.user_registry.web.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "user")
public class UserController {

    private static final String NAMESPACE_HEADER_NAME = "x-pagopa-namespace";

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "${swagger.api.user.findById.summary}",
            notes = "${swagger.api.user.findById.notes}")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource findById(@ApiParam("${swagger.model.user.id}")
                                 @PathVariable("id")
                                         UUID id,
                                 @ApiParam(value = "${swagger.model.user.fl}")
                                 @RequestParam(value = "fl")
                                         List<String> fields) {
        //TODO: manage fields
        User user = userService.findById(id.toString(), fields.isEmpty() || fields.contains("fiscalCode"));
        return UserMapper.map(user);
    }


    @ApiOperation(value = "${swagger.api.user.search.summary}",
            notes = "${swagger.api.user.search.notes}")
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource search(@ApiParam("${swagger.model.namespace}")
                               @RequestHeader(NAMESPACE_HEADER_NAME)
                                       String namespace,
                               @ApiParam("${swagger.model.user.fl}")
                               @RequestParam("fl")
                                       List<String> fields,
                               @RequestBody
                                       UserSearchDto request) {
        //TODO: manage fields
        User user = userService.search(request.getFiscalCode(), namespace);
        return UserMapper.map(user);
    }


    @ApiOperation(value = "${swagger.api.user.update.summary}",
            notes = "${swagger.api.user.update.notes}")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@ApiParam("${swagger.model.user.id}")
                       @PathVariable("id")
                               UUID id,
                       @RequestBody
                               MutableUserFieldsDto request) {
        User user = UserMapper.map(request);
        userService.save(id.toString(), user);
    }


    @ApiOperation(value = "${swagger.api.user.upsert.summary}",
            notes = "${swagger.api.user.upsert.notes}")
    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserId upsert(@ApiParam("${swagger.model.namespace}")
                         @RequestHeader(NAMESPACE_HEADER_NAME)
                                 String namespace,
                         @RequestBody
                                 SaveUserDto request) {
        User user = UserMapper.map(request);
        String id = userService.upsert(user, namespace);
        UserId userId = new UserId();
        userId.setId(UUID.fromString(id));
        return userId;
    }


    @ApiOperation(value = "${swagger.api.user.deleteById.summary}",
            notes = "${swagger.api.user.deleteById.notes}")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@ApiParam("${swagger.model.user.id}")
                           @PathVariable("id")
                                   UUID id) {
        throw new UnsupportedOperationException();
    }

}
