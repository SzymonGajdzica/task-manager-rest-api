package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.RequestService;
import pl.polsl.task.manager.rest.api.views.ActionProgressPatch;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/request")
public class RequestController {

    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView createRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                     @RequestBody RequestPost requestPost) {
        return requestService.createRequest(token, requestPost);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<RequestView> getRequests(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @RequestParam(required = false) Long objectId) {
        if (objectId != null)
            return requestService.getRequests(token, objectId);
        else
            return requestService.getRequests(token);
    }

    @PatchMapping(value = "/progress/{requestId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView updateRequestProgress(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                             @PathVariable Long requestId,
                                             @RequestBody ActionProgressPatch actionProgressPatch) {
        return requestService.getPatchedRequest(token, requestId, actionProgressPatch);
    }

    @PatchMapping(value = "/{requestId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public RequestView updateRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                     @PathVariable Long requestId,
                                     @RequestBody RequestPatch requestPatch) {
        return requestService.getPatchedRequest(token, requestId, requestPatch);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{requestId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                              @PathVariable Long requestId) {
        requestService.deleteRequest(token, requestId);
    }


}
