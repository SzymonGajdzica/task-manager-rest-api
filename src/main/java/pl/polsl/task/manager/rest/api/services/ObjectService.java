package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

import java.util.List;

public interface ObjectService {

    @NonNull
    Object createObject(String token, ObjectPost objectPost);

    @NonNull
    Object getPatchedObject(String token, Long objectId, ObjectPatch objectPatch);

    @NonNull
    List<Object> getObjects(String token);

    void deleteObject(String token, Long objectId);

    @NonNull
    ObjectView serialize(Object object);

}
