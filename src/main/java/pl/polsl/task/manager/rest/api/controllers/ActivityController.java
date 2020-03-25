package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.services.ActivityService;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityProgressPatch;
import pl.polsl.task.manager.rest.api.views.ActivityView;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping(value = "/activities")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView createActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                       @RequestBody ActivityPost activityPost) {
        Activity activity = activityService.createActivity(token, activityPost);
        return activityService.serialize(activity);
    }

    @PatchMapping(value = "/progress/{activityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView updateActivityProgress(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                               @PathVariable Long activityId,
                                               @RequestBody ActivityProgressPatch activityProgressPatch) {
        Activity activity = activityService.getPatchedActivityProgress(token, activityId, activityProgressPatch);
        return activityService.serialize(activity);
    }

    @PatchMapping(value = "/{activityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView updateActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                           @PathVariable Long activityId,
                                           @RequestBody ActivityPatch activityPatch) {
        Activity activity = activityService.getPatchedActivity(token, activityId, activityPatch);
        return activityService.serialize(activity);
    }

}
