package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.AuthenticationService;
import pl.polsl.task.manager.rest.api.services.UserService;
import pl.polsl.task.manager.rest.api.views.AuthenticationPost;
import pl.polsl.task.manager.rest.api.views.AuthenticationView;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserView;

@CrossOrigin
@RestController
@RequestMapping(value = "/authenticate")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    public AuthenticationController(AuthenticationService authenticationService, UserService userService) {
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView registerUser(@RequestBody UserPost userPost) {
        return userService.serialize(authenticationService.registerUser(userPost));
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthenticationView createAuthenticationToken(@RequestBody AuthenticationPost authenticationPost) {
        return authenticationService.loginUser(authenticationPost);
    }

}