package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

import java.util.List;

public interface ObjectService {

    @NonNull
    ObjectView createObject(String token, ObjectPost objectPost);

    @NonNull
    ObjectView getPatchedObject(String token, Long objectId, ObjectPatch objectPatch);

    @NonNull
    List<ObjectView> getObjects(String token);

    void deleteObject(String token, Long objectId);

}
