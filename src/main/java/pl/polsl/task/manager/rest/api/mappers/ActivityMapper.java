package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;

public interface ActivityMapper {

    @NonNull
    Activity map(ActivityPost activityPost);

    void map(ActivityPatch activityPatch, Activity activity);

    @NonNull
    ActivityView map(Activity activity);

}
