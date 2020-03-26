package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.ActivityType;
import pl.polsl.task.manager.rest.api.services.ActivityTypeService;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/activity_type")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView createActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @RequestBody CodeNamePost codeNamePost) {
        ActivityType activityType = activityTypeService.createActivityType(token, codeNamePost);
        return activityTypeService.serialize(activityType);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeNameView> getActivityTypes(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<ActivityType> activityTypes = activityTypeService.getActivitiesTypes(token);
        return activityTypes.stream().map(activityTypeService::serialize).collect(Collectors.toList());
    }

    @PatchMapping(value = "/{activityTypeCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView updateActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @PathVariable String activityTypeCode,
                                           @RequestBody CodeNamePatch codeNamePatch) {
        ActivityType activityType = activityTypeService.getPatchedActivityType(token, activityTypeCode, codeNamePatch);
        return activityTypeService.serialize(activityType);
    }

}
