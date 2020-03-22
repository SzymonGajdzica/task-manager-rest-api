package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.UserService;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping(value = "/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PatchMapping(value = "/updateRole/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateRole(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @PathVariable Long id,
                               @RequestBody UserRolePatch userRolePatch) {
        return userService.serialize(userService.getUserWithPatchedRole(userService.getUser(token), userService.getUser(id), userRolePatch));
    }

    @GetMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getUser(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        return userService.serialize(userService.getUser(token));
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        userService.removeUser(userService.getUser(token));
    }

    @PatchMapping(value = "/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateUser(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @RequestBody UserPatch userPatch) {
        return userService.serialize(userService.getPatchedUser(userService.getUser(token), userPatch));
    }

}
