package it.pagopa.pdv.user_registry.web.controller;

import io.swagger.annotations.*;
import it.pagopa.pdv.user_registry.core.UserService;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;
import it.pagopa.pdv.user_registry.web.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.EnumSet;
import java.util.UUID;

import static it.pagopa.pdv.user_registry.core.logging.LogUtils.CONFIDENTIAL_MARKER;

@Slf4j
@RestController
@RequestMapping(value = "users", produces = MediaType.APPLICATION_JSON_VALUE)
@Api(tags = "user")
@ApiResponses({@ApiResponse(code = 400, message = "Bad Request")})
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
                                         EnumSet<UserResource.Fields> fields) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, fields = {}", id, fields);
        User user = userService.findById(id.toString(), fields.contains(UserResource.Fields.fiscalCode));
        UserResource userResource = UserMapper.map(user, fields);
        log.debug(CONFIDENTIAL_MARKER, "[findById] output = {}", userResource);
        log.trace("[findById] end");
        return userResource;
    }


    @ApiOperation(value = "${swagger.api.user.search.summary}",
            notes = "${swagger.api.user.search.notes}")
    @ApiResponses({@ApiResponse(code = 404, message = "Not Found")})
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource search(@ApiParam("${swagger.model.namespace}")
                               @RequestHeader(NAMESPACE_HEADER_NAME)
                                       String namespace,
                               @ApiParam("${swagger.model.user.fl}")
                               @RequestParam("fl")
                                       EnumSet<UserResource.Fields> fields,
                               @RequestBody
                                       UserSearchDto request) {
        log.trace("[search] start");
        log.debug(CONFIDENTIAL_MARKER, "[search] inputs: namespace = {}, fields = {}, request = {}", namespace, fields, request);
        User user = userService.search(request.getFiscalCode(), namespace);
        UserResource userResource = UserMapper.map(user, fields);
        log.debug(CONFIDENTIAL_MARKER, "[search] output = {}", userResource);
        log.trace("[search] end");
        return userResource;
    }


    @ApiOperation(value = "${swagger.api.user.update.summary}",
            notes = "${swagger.api.user.update.notes}")
    @ApiResponses({@ApiResponse(code = 409, message = "Conflict")})
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@ApiParam("${swagger.model.user.id}")
                       @PathVariable("id")
                               UUID id,
                       @RequestBody
                               MutableUserFieldsDto request) {
        log.trace("[update] start");
        log.debug("[update] inputs: id = {}, request = {}", id, request);
        User user = UserMapper.map(request);
        userService.update(id.toString(), user);
        log.trace("[update] end");
    }


    @ApiOperation(value = "${swagger.api.user.save.summary}",
            notes = "${swagger.api.user.save.notes}")
    @ApiResponses({@ApiResponse(code = 409, message = "Conflict")})
    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserId save(@ApiParam("${swagger.model.namespace}")
                       @RequestHeader(NAMESPACE_HEADER_NAME)
                               String namespace,
                       @RequestBody
                               SaveUserDto request) {
        log.trace("[save] start");
        log.debug(CONFIDENTIAL_MARKER, "[save] inputs: namespace = {}, request = {}", namespace, request);
        User user = UserMapper.map(request);
        String id = userService.save(user, namespace);
        UserId userId = new UserId();
        userId.setId(UUID.fromString(id));
        log.debug("[save] output = {}", userId);
        log.trace("[save] end");
        return userId;
    }


    @ApiOperation(value = "${swagger.api.user.deleteById.summary}",
            notes = "${swagger.api.user.deleteById.notes}")
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@ApiParam("${swagger.model.user.id}")
                           @PathVariable("id")
                                   UUID id) {
        //TODO
        throw new UnsupportedOperationException();
    }

}
