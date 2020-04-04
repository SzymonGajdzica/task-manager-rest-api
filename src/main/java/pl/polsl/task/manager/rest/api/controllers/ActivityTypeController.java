package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.ActivityTypeService;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/activity_type")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView createActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @RequestBody CodeNamePost codeNamePost) {
        return activityTypeService.createActivityType(token, codeNamePost);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeNameView> getActivityTypes(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        return activityTypeService.getActivitiesTypes(token);
    }

    @PatchMapping(value = "/{activityTypeCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView updateActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @PathVariable String activityTypeCode,
                                           @RequestBody CodeNamePatch codeNamePatch) {
        return activityTypeService.getPatchedActivityType(token, activityTypeCode, codeNamePatch);
    }

}
