package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

public interface ActivityTypeService {

    @NonNull
    CodeNameView createActivityType(String token, CodeNamePost codeNamePost);

    @NonNull
    CodeNameView getPatchedActivityType(String token, String activityTypeCode, CodeNamePatch codeNamePatch);

    @NonNull
    List<CodeNameView> getActivitiesTypes(String token);

}
