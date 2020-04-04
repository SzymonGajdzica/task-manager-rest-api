package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;

import java.util.List;

public interface ActivityService {

    @NonNull
    ActivityView createActivity(String token, ActivityPost activityPost);

    @NonNull
    ActivityView getPatchedActivity(String token, Long activityId, ActionPatch actionPatch);

    @NonNull
    ActivityView getPatchedActivity(String token, Long activityId, ActivityPatch activityPatch);

    @NonNull
    List<ActivityView> getRequestActivities(String token, Long requestId);

    @NonNull
    List<ActivityView> getWorkerActivities(String token);

    void deleteActivity(String token, Long activityId);

}
