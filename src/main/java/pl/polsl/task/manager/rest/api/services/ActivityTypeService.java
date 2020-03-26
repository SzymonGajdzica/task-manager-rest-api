package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.ActivityType;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

public interface ActivityTypeService {

    @NonNull
    ActivityType createActivityType(String token, CodeNamePost codeNamePost);

    @NonNull
    ActivityType getPatchedActivityType(String token, String activityTypeCode, CodeNamePatch codeNamePatch);

    @NonNull
    List<ActivityType> getActivitiesTypes(String token);

    @NonNull
    CodeNameView serialize(ActivityType activityType);

}
