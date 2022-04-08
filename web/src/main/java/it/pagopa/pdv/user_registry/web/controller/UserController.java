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
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "users")
public class UserController {

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @ApiOperation(value = "${swagger.users.api.getUserById.summary}",
            notes = "${swagger.users.api.getUserById.notes}")
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource getUserById(@ApiParam("${swagger.model.user.id}")
                                    @PathVariable("id")
                                            UUID id,
                                    @ApiParam(value = "${swagger.model.user.fl}")
                                    @RequestParam(value = "fl", required = true)
                                            Optional<List<String>> fields) {
        //TODO: manage fields
        User user = userService.findById(id.toString(), fields.isEmpty() || fields.get().contains("fiscalCode"));
        return UserMapper.map(user);
    }


    @ApiOperation(value = "${swagger.users.api.searchUser.summary}",
            notes = "${swagger.users.api.searchUser.notes}")
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource searchUser(@RequestHeader("x-pagopa-namespace")
                                           String namespace,
                                   @ApiParam("${swagger.model.user.fl}")
                                   @RequestParam("fl")
                                           Optional<List<String>> fields,
                                   @RequestBody
                                           UserSearchDto request) {
        //TODO: manage fields
        User user = userService.search(request.getFiscalCode(), namespace);
        return UserMapper.map(user);
    }


    @ApiOperation(value = "${swagger.users.api.updateUser.summary}",
            notes = "${swagger.users.api.updateUser.notes}")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(@ApiParam("${swagger.model.user.id}")
                           @PathVariable("id")
                                   UUID id,
                           @RequestBody
                                   MutableUserFieldsDto request) {
        User user = UserMapper.map(request);
        userService.save(id.toString(), user);
    }


    @ApiOperation(value = "${swagger.users.api.upsertUser.summary}",
            notes = "${swagger.users.api.upsertUser.notes}")
    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserInternalId upsertUser(@RequestHeader("x-pagopa-namespace")
                                             String namespace,
                                     @RequestBody
                                             SaveUserDto request) {
        User user = UserMapper.map(request);
        String id = userService.upsert(user, namespace);
        UserInternalId userInternalId = new UserInternalId();
        userInternalId.setInternalId(UUID.fromString(id));
        return userInternalId;
    }


    @ApiOperation(value = "${swagger.users.api.deleteById.summary}",
            notes = "${swagger.users.api.deleteById.notes}")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@ApiParam("${swagger.model.user.id}")
                           @PathVariable("id")
                                   UUID id) {
        throw new UnsupportedOperationException();
    }

}
