package it.pagopa.pdv.user_registry.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import it.pagopa.pdv.user_registry.core.UserService;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.model.*;
import it.pagopa.pdv.user_registry.web.model.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.EnumSet;
import java.util.UUID;

import static it.pagopa.pdv.user_registry.core.logging.LogUtils.CONFIDENTIAL_MARKER;

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
    @ApiResponse(responseCode = "404",
            description = "Not Found",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource search(@ApiParam("${swagger.model.namespace}")
                               @RequestHeader(NAMESPACE_HEADER_NAME)
                                       String namespace,
                               @ApiParam("${swagger.model.user.fl}")
                               @RequestParam("fl")
                                       EnumSet<UserResource.Fields> fields,
                               @RequestBody
                               @Valid
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
    @ApiResponse(responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@ApiParam("${swagger.model.user.id}")
                       @PathVariable("id")
                               UUID id,
                       @RequestBody
                       @Valid
                               MutableUserFieldsDto request) {
        log.trace("[update] start");
        log.debug("[update] inputs: id = {}, request = {}", id, request);
        User user = UserMapper.map(request);
        userService.update(id.toString(), user);
        log.trace("[update] end");
    }


    @ApiOperation(value = "${swagger.api.user.save.summary}",
            notes = "${swagger.api.user.save.notes}")
    @ApiResponse(responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserId save(@ApiParam("${swagger.model.namespace}")
                       @RequestHeader(NAMESPACE_HEADER_NAME)
                               String namespace,
                       @RequestBody
                       @Valid
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
