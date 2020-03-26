package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Admin;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.models.User;
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
    private final ActionService actionService;

    public RequestServiceImpl(RequestRepository requestRepository, AuthenticationService authenticationService, ActionService actionService) {
        this.requestRepository = requestRepository;
        this.authenticationService = authenticationService;
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
        return requestRepository.save(request);
    }

    @Override
    public Request getPatchedRequest(String token, Long requestId, ActionPatch actionPatch) {
        User manager = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getResult() != null)
            throw new BadRequestException("Cannot update progress in already finished request");
        if (request.getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can update its progress");
        return requestRepository.save(actionService.getPatchedAction(request, actionPatch));
    }

    @Override
    public Request getPatchedRequest(String token, Long requestId, RequestPatch requestPatch) {
        User manager = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can update it");
        if (requestPatch.getDescription() != null)
            request.setDescription(requestPatch.getDescription());
        return requestRepository.save(request);
    }

    @Override
    public void deleteRequest(String token, Long requestId) {
        User manager = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getManager() != manager)
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
    public List<Request> getAllRequests(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (currentUser instanceof Manager)
            return requestRepository.findAllByManagerId(currentUser.getId());
        if (currentUser instanceof Admin)
            return requestRepository.findAll();
        throw new ForbiddenAccessException(Manager.class, Admin.class);
    }

}
