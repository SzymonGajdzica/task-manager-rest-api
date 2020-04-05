package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

public interface RequestMapper {

    @NonNull
    Request map(RequestPost requestPost);

    void map(RequestPatch requestPatch, Request request);

    @NonNull
    RequestView map(Request request);

}
