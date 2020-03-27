package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

import java.util.List;

public interface RequestService {

    @NonNull
    Request createRequest(String token, RequestPost requestPost);

    @NonNull
    Request getPatchedRequest(String token, Long requestId, ActionPatch actionPatch);

    @NonNull
    Request getPatchedRequest(String token, Long requestId, RequestPatch requestPatch);

    void deleteRequest(String token, Long requestId);

    @NonNull
    List<Request> getRequests(String token);

    @NonNull
    Request getRequest(String token, Long requestId);

    @NonNull
    RequestView serialize(Request request);


}
