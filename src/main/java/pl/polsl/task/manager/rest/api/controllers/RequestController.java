package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.services.RequestService;
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

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView createRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                     @RequestBody RequestPost requestPost) {
        Request request = requestService.createRequest(token, requestPost);
        return requestService.serialize(request);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestView> getAllRequests(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<Request> requests = requestService.getAllRequests(token);
        return requests.stream().map(requestService::serialize).collect(Collectors.toList());
    }


}
