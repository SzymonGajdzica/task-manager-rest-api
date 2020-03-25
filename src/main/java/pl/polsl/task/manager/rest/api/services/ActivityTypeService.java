package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.ActivityType;
import pl.polsl.task.manager.rest.api.views.ActivityTypePatch;
import pl.polsl.task.manager.rest.api.views.ActivityTypePost;
import pl.polsl.task.manager.rest.api.views.ActivityTypeView;

import java.util.List;

public interface ActivityTypeService {

    @NonNull
    ActivityType createActivityType(String token, ActivityTypePost activityTypePost);

    @NonNull
    ActivityType getPatchedActivity(String token, String activityTypeCode, ActivityTypePatch activityTypePatch);

    @NonNull
    List<ActivityType> getAllActivityTypes(String token);

    @NonNull
    ActivityTypeView serialize(ActivityType activityType);

}
