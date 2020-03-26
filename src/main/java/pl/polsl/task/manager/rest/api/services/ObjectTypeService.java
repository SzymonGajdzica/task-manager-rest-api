package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.ObjectType;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

public interface ObjectTypeService {

    @NonNull
    ObjectType createObjectType(String token, CodeNamePost codeNamePost);

    @NonNull
    ObjectType getPatchedObjectType(String token, String objectTypeCode, CodeNamePatch codeNamePatch);

    @NonNull
    List<ObjectType> getObjectsTypes(String token);

    @NonNull
    CodeNameView serialize(ObjectType objectType);

}
