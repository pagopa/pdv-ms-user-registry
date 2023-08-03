package it.pagopa.pdv.user_registry.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import it.pagopa.pdv.user_registry.core.UserService;
import it.pagopa.pdv.user_registry.core.model.User;
import it.pagopa.pdv.user_registry.web.annotations.CommonApiResponsesWrapper;
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
@Tag(name = "user")
public class UserController {

    private static final String NAMESPACE_HEADER_NAME = "x-pagopa-namespace";

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "${swagger.api.user.findById.summary}",
            description = "${swagger.api.user.findById.notes}")
    @CommonApiResponsesWrapper
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            }
    )
    @GetMapping(value = "{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserResource findById(@Parameter(description = "${swagger.model.namespace}")
                                 @RequestHeader(NAMESPACE_HEADER_NAME)
                                 String namespace,
                                 @Parameter(description = "${swagger.model.user.id}",in = ParameterIn.PATH)
                                 @PathVariable("id")
                                 UUID id,
                                 @Parameter(description = "${swagger.model.user.fl}")
                                 @RequestParam(value = "fl")
                                 EnumSet<UserResource.Fields> fields) {
        log.trace("[findById] start");
        log.debug("[findById] inputs: id = {}, fields = {}", id, fields);
        User user = userService.findById(id.toString(), namespace, fields.contains(UserResource.Fields.fiscalCode));
        UserResource userResource = UserMapper.map(user, fields);
        log.debug(CONFIDENTIAL_MARKER, "[findById] output = {}", userResource);
        log.trace("[findById] end");
        return userResource;
    }


    @Operation(summary = "${swagger.api.user.search.summary}",
            description = "${swagger.api.user.search.notes}")
    @CommonApiResponsesWrapper
    @ApiResponse(
            responseCode = "404",
            description = "Not Found",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            }
    )
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource search(@Parameter(description = "${swagger.model.namespace}")
                               @RequestHeader(NAMESPACE_HEADER_NAME)
                               String namespace,
                               @Parameter(description = "${swagger.model.user.fl}")
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


    @Operation(summary = "${swagger.api.user.update.summary}",
            description = "${swagger.api.user.update.notes}")
    @CommonApiResponsesWrapper
    @ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Parameter(description = "${swagger.model.namespace}")
                           @RequestHeader(NAMESPACE_HEADER_NAME)
                           String namespace,
                       @Parameter(description = "${swagger.model.user.id}", in = ParameterIn.PATH)
                       @PathVariable("id")
                       UUID id,
                       @RequestBody
                       @Valid
                       MutableUserFieldsDto request) {
        log.trace("[update] start");
        log.debug("[update] inputs: id = {}, request = {}", id, request);
        User user = UserMapper.map(request);
        userService.update(id.toString(), user, namespace);
        log.trace("[update] end");
    }


    @Operation(summary = "${swagger.api.user.save.summary}",
            description = "${swagger.api.user.save.notes}")
    @CommonApiResponsesWrapper
    @ApiResponse(
            responseCode = "409",
            description = "Conflict",
            content = {
                    @Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema = @Schema(implementation = Problem.class))
            })
    @PatchMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserId save(@Parameter(description = "${swagger.model.namespace}")
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


    @Operation(summary = "${swagger.api.user.deleteById.summary}",
            description = "${swagger.api.user.deleteById.notes}")
    @CommonApiResponsesWrapper
    @DeleteMapping(value = "{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@Parameter(description = "${swagger.model.user.id}",in = ParameterIn.PATH)
                           @PathVariable("id")
                           UUID id) {
        //TODO
        throw new UnsupportedOperationException();
    }

}
