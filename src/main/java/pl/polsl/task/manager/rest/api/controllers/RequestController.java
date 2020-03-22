package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.RequestService;
import pl.polsl.task.manager.rest.api.services.UserService;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/requests")
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;

    public RequestController(RequestService requestService, UserService userService) {
        this.requestService = requestService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView createRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                     @RequestBody RequestPost requestPost) {
        return requestService.serialize(requestService.createRequest(userService.getUser(token), requestPost));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestView> getAllRequests(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        return requestService.getAllRequests(userService.getUser(token)).stream().map(requestService::serialize).collect(Collectors.toList());
    }


}
