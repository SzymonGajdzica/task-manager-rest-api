package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.ActivityType;
import pl.polsl.task.manager.rest.api.services.ActivityTypeService;
import pl.polsl.task.manager.rest.api.views.ActivityTypePatch;
import pl.polsl.task.manager.rest.api.views.ActivityTypePost;
import pl.polsl.task.manager.rest.api.views.ActivityTypeView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value = "/activityTypes")
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityTypeView createActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @RequestBody ActivityTypePost activityTypePost) {
        ActivityType activityType = activityTypeService.createActivityType(token, activityTypePost);
        return activityTypeService.serialize(activityType);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActivityTypeView> getActivityTypes(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<ActivityType> activityTypes = activityTypeService.getAllActivityTypes(token);
        return activityTypes.stream().map(activityTypeService::serialize).collect(Collectors.toList());
    }

    @PatchMapping(value = "/{activityTypeCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityTypeView updateActivityType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                               @PathVariable String activityTypeCode,
                                               @RequestBody ActivityTypePatch activityTypePatch) {
        ActivityType activityType = activityTypeService.getPatchedActivity(token, activityTypeCode, activityTypePatch);
        return activityTypeService.serialize(activityType);
    }

}
