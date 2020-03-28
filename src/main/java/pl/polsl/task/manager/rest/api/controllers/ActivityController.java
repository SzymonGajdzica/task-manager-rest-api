package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.services.ActivityService;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/activity")
public class ActivityController {

    private final ActivityService activityService;

    public ActivityController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView createActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                       @RequestBody ActivityPost activityPost) {
        Activity activity = activityService.createActivity(token, activityPost);
        return activityService.serialize(activity);
    }

    @PatchMapping(value = "/progress/{activityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView updateActivityProgress(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                               @PathVariable Long activityId,
                                               @RequestBody ActionPatch actionPatch) {
        Activity activity = activityService.getPatchedActivity(token, activityId, actionPatch);
        return activityService.serialize(activity);
    }

    @PatchMapping(value = "/{activityId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView updateActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                       @PathVariable Long activityId,
                                       @RequestBody ActivityPatch activityPatch) {
        Activity activity = activityService.getPatchedActivity(token, activityId, activityPatch);
        return activityService.serialize(activity);
    }

    @GetMapping(value = "/request", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActivityView> getRequestActivities(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                                   @RequestParam Long requestId) {
        List<Activity> activities = activityService.getRequestActivities(token, requestId);
        return activities.stream().map(activityService::serialize).collect(Collectors.toList());
    }

    @GetMapping(value = "/worker", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActivityView> getWorkerActivities(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<Activity> activities = activityService.getWorkerActivities(token);
        return activities.stream().map(activityService::serialize).collect(Collectors.toList());
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{activityId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                               @PathVariable Long activityId) {
        activityService.deleteActivity(token, activityId);
    }


}
