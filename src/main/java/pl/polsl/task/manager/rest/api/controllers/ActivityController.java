package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.ActivityService;
import pl.polsl.task.manager.rest.api.services.UserService;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;
import springfox.documentation.annotations.ApiIgnore;

@CrossOrigin
@RestController
@RequestMapping(value = "/activities")
public class ActivityController {

    private final ActivityService activityService;
    private final UserService userService;

    public ActivityController(ActivityService activityService, UserService userService) {
        this.activityService = activityService;
        this.userService = userService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ActivityView createActivity(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                       @RequestBody ActivityPost activityPost) {
        return activityService.serialize(activityService.createActivity(userService.getUser(token), activityPost));
    }

}
