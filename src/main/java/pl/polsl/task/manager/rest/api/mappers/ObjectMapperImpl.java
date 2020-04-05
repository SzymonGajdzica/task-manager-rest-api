package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;

@Component
public class ObjectMapperImpl implements ObjectMapper {

    @Override
    public Object map(ObjectPost objectPost) {
        Object object = new Object();
        object.setName(objectPost.getName());
        return object;
    }

    @Override
    public void map(ObjectPatch objectPatch, Object object) {
        if (objectPatch.getName() != null)
            object.setName(objectPatch.getName());
    }

    @Override
    public ObjectView map(Object object) {
        ObjectView objectView = new ObjectView();
        objectView.setName(object.getName());
        objectView.setClientId(object.getClient().getId());
        objectView.setId(object.getId());
        objectView.setObjectTypeCode(object.getObjectType().getCode());
        return objectView;
    }

}
