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
                                    @RequestParam(value = "fl", required = false)
                                            Optional<List<String>> fields) {
        User user = userService.getUserById(id.toString(), fields);
        return UserMapper.toResource(user);
    }


//    @ApiOperation(value = "${swagger.users.api.getFiscalCodeById.summary}",
//            notes = "${swagger.users.api.getFiscalCodeById.notes}")
//    @GetMapping(value = "/{id}/fiscal-code")
//    @ResponseStatus(HttpStatus.OK)
//    public UserExternalId getFiscalCodeById(@ApiParam("${swagger.model.user.id}")
//                                                    @PathVariable("id")
//                                                            UUID id,
//                                                    @ApiParam("${swagger.model.user.fl}")
//                                                    @RequestParam("fl")
//                                                            Optional<List<String>> fields) {//FIXME: remove
//        //TODO useless?! can use getUserById with fl=id
//        return null;
//    }


//    @ApiOperation(value = "${swagger.users.api.getIdByFiscalCode.summary}",
//            notes = "${swagger.users.api.getIdByFiscalCode.notes}")
//    @PostMapping(value = "/token/search")
//    @ResponseStatus(HttpStatus.OK)
//    public UserInternalId getIdByFiscalCode(@ApiParam("${swagger.model.user.namespace}")
//                                                    @RequestHeader("x-pagopa-namespace")
//                                                            String namespace,
//                                                    @ApiParam("${swagger.model.user.fl}")
//                                                    @RequestParam("fl")
//                                                            Optional<List<String>> fields,//FIXME: remove
//                                                    @RequestBody
//                                                            UserSearchDto request) {
//        //TODO useless?! can use searchUser with fl=id
//        return null;
//    }


    @ApiOperation(value = "${swagger.users.api.searchUser.summary}",
            notes = "${swagger.users.api.searchUser.notes}")
    @PostMapping(value = "search")
    @ResponseStatus(HttpStatus.OK)
    public UserResource searchUser(@ApiParam("${swagger.model.user.fl}")
                                   @RequestParam("fl")
                                           Optional<List<String>> fields,
                                   @RequestBody
                                           UserSearchDto request) {
        return null;
    }


    @ApiOperation(value = "${swagger.users.api.updateUserById.summary}",
            notes = "${swagger.users.api.updateUserById.notes}")
    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void saveUserById(@ApiParam("${swagger.model.user.id}")
                             @PathVariable("id")
                                     UUID id,
                             @RequestBody
                                     MutableUserFieldsDto request) {
    }

    @ApiOperation(value = "${swagger.users.api.updateUserById.summary}",
            notes = "${swagger.users.api.updateUserById.notes}")
    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public UserInternalId upsertUser(@RequestBody
                                             UserExternalId request,
                                     @RequestHeader("namespace")
                                             String namespace) {
        String id = userService.upsertUser(request.getFiscalCode(), namespace);
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
    }

}
