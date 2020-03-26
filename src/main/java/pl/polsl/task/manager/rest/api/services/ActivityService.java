package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityProgressPatch;
import pl.polsl.task.manager.rest.api.views.ActivityView;

import java.util.List;

public interface ActivityService {

    @NonNull
    Activity createActivity(String token, ActivityPost activityPost);

    @NonNull
    Activity getPatchedActivityProgress(String token, Long activityId, ActivityProgressPatch activityProgressPatch);

    @NonNull
    Activity getPatchedActivity(String token, Long activityId, ActivityPatch activityPatch);

    @NonNull
    List<Activity> getRequestActivities(String token, Long requestId);

    @NonNull
    List<Activity> getWorkerActivities(String token);

    @NonNull
    ActivityView serialize(Activity activity);

}
