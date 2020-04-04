package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

import java.util.List;

public interface RequestService {

    @NonNull
    RequestView createRequest(String token, RequestPost requestPost);

    @NonNull
    RequestView getPatchedRequest(String token, Long requestId, ActionPatch actionPatch);

    @NonNull
    RequestView getPatchedRequest(String token, Long requestId, RequestPatch requestPatch);

    void deleteRequest(String token, Long requestId);

    @NonNull
    List<RequestView> getRequests(String token);

    @NonNull
    List<RequestView> getRequests(String token, Long objectId);

}
