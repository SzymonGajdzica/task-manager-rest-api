package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.User;
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

    @PatchMapping(value = "/updateRole/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateRole(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @PathVariable Long userId,
                               @RequestBody UserRolePatch userRolePatch) {
        User user = userService.getUserWithPatchedRole(token, userId, userRolePatch);
        return userService.serialize(user);
    }

    @GetMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getUser(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        User user = userService.getUser(token);
        return userService.serialize(user);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteUser(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        userService.deleteUser(token);
    }

    @PatchMapping(value = "/self", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateUser(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @RequestBody UserPatch userPatch) {
        User user = userService.getPatchedUser(token, userPatch);
        return userService.serialize(user);
    }

}
