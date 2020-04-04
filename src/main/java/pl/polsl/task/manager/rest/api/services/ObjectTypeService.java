package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

public interface ObjectTypeService {

    @NonNull
    CodeNameView createObjectType(String token, CodeNamePost codeNamePost);

    @NonNull
    CodeNameView getPatchedObjectType(String token, String objectTypeCode, CodeNamePatch codeNamePatch);

    @NonNull
    List<CodeNameView> getObjectsTypes(String token);

}
