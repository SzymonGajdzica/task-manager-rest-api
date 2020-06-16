package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.UserService;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView registerUser(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                 @RequestBody UserPost userPost) {
        return userService.createUser(token, userPost);
    }

    @GetMapping(value = "/self", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getUser(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        return userService.getUser(token);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserView> getUsers() {
        return userService.getUsers();
    }

    @PatchMapping(value = "/{userId}/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView updateUser(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @PathVariable Long userId,
                               @RequestBody UserPatch userPatch) {
        return userService.getPatchedUser(token, userId, userPatch);
    }

}
