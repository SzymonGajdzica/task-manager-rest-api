package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.services.RequestService;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/request")
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

    @GetMapping(value = "/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestView> getRequests(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @PathVariable Long requestId) {
        List<Request> requests = requestService.getRequests(token);
        return requests.stream().map(requestService::serialize).collect(Collectors.toList());
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestView> getRequests(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<Request> requests = requestService.getRequests(token);
        return requests.stream().map(requestService::serialize).collect(Collectors.toList());
    }

    @PatchMapping(value = "/progress/{requestId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView updateRequestProgress(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                             @PathVariable Long requestId,
                                             @RequestBody ActionPatch actionPatch) {
        Request request = requestService.getPatchedRequest(token, requestId, actionPatch);
        return requestService.serialize(request);
    }

    @PatchMapping(value = "/{requestId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView updateRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                     @PathVariable Long requestId,
                                     @RequestBody RequestPatch requestPatch) {
        Request request = requestService.getPatchedRequest(token, requestId, requestPatch);
        return requestService.serialize(request);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                              @PathVariable Long requestId) {
        requestService.deleteRequest(token, requestId);
    }


}
