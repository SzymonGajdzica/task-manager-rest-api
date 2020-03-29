package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.ObjectRepository;
import pl.polsl.task.manager.rest.api.repositories.RequestRepository;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.RequestPatch;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

import java.util.List;

@Component
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    private final AuthenticationService authenticationService;
    private final ObjectRepository objectRepository;
    private final ActionService actionService;

    public RequestServiceImpl(RequestRepository requestRepository, AuthenticationService authenticationService, ObjectRepository objectRepository, ActionService actionService) {
        this.requestRepository = requestRepository;
        this.authenticationService = authenticationService;
        this.objectRepository = objectRepository;
        this.actionService = actionService;
    }

    @Override
    public Request createRequest(String token, RequestPost requestPost) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
        Request request = new Request();
        request.setManager((Manager) currentUser);
        request.setDescription(requestPost.getDescription());
        request.setActionStatus(actionService.getInitialStatus());
        request.setObject(objectRepository.getById(requestPost.getObjectId()));
        return requestRepository.save(request);
    }

    @Override
    public Request getPatchedRequest(String token, Long requestId, ActionPatch actionPatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getEndDate() != null)
            throw new BadRequestException("Cannot update progress in already finished request");
        if (!currentUser.equals(request.getManager()))
            throw new ForbiddenAccessException("Only manager that created request can update its progress");
        return requestRepository.save(actionService.getPatchedAction(request, actionPatch));
    }

    @Override
    public Request getPatchedRequest(String token, Long requestId, RequestPatch requestPatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (!currentUser.equals(request.getManager()))
            throw new ForbiddenAccessException("Only manager that created request can update it");
        if (requestPatch.getDescription() != null)
            request.setDescription(requestPatch.getDescription());
        return requestRepository.save(request);
    }

    @Override
    public void deleteRequest(String token, Long requestId) {
        User currentUser = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (!currentUser.equals(request.getManager()))
            throw new ForbiddenAccessException("Only manager that created request can remove it");
        requestRepository.delete(request);
    }

    @Override
    public RequestView serialize(Request request) {
        RequestView requestView = actionService.serialize(request, new RequestView());
        requestView.setObjectId(request.getObject().getId());
        return requestView;
    }

    @Override
    public List<Request> getRequests(String token, Long objectId) {
        User currentUser = authenticationService.getUserFromToken(token);
        Object object = objectRepository.getById(objectId);
        if (currentUser instanceof Manager)
            return requestRepository.findAllByManagerAndObject((Manager) currentUser, object);
        if (currentUser instanceof Client) {
            if (!object.getClient().equals(currentUser))
                throw new ForbiddenAccessException("Client can browse only objects assigned to his account");
            return object.getRequests();
        }
        if (currentUser instanceof Worker)
            return requestRepository.findAllByActivitiesContainsAndObject(((Worker) currentUser).getActivities(), object);
        throw new ForbiddenAccessException(Manager.class, Worker.class, Client.class);
    }

    @Override
    public List<Request> getRequests(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (currentUser instanceof Client)
            return requestRepository.findAllByObjectClient((Client) currentUser);
        if (currentUser instanceof Manager)
            return ((Manager) currentUser).getRequests();
        if (currentUser instanceof Worker)
            return requestRepository.findAllByActivitiesContains(((Worker) currentUser).getActivities());
        throw new ForbiddenAccessException(Manager.class, Worker.class, Client.class);
    }

}
