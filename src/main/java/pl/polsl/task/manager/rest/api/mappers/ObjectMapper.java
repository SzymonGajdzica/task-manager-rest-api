package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

public interface ObjectMapper {

    @NonNull
    Object map(ObjectPost objectPost);

    void map(ObjectPatch objectPatch, Object object);

    @NonNull
    ObjectView map(Object object);

}
