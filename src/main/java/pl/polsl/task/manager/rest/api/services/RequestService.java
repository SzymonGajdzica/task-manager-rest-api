package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

import java.util.List;

public interface RequestService {

    @NonNull
    Request createRequest(String token, RequestPost requestPost);

    @NonNull
    RequestView serialize(Request request);

    @NonNull
    List<Request> getAllRequests(String token);

}
