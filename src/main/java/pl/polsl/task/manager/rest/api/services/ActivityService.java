package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;

public interface ActivityService {

    @NonNull
    Activity createActivity(User manager, ActivityPost activityPost);

    @NonNull
    ActivityView serialize(Activity activity);

}
