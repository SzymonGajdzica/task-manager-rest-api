package pl.polsl.task.manager.rest.api.services;

import org.modelmapper.ModelMapper;
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
import java.util.stream.Collectors;

@Component
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    private final AuthenticationService authenticationService;
    private final ObjectRepository objectRepository;
    private final ActionService actionService;
    private final ModelMapper modelMapper;

    public RequestServiceImpl(RequestRepository requestRepository, AuthenticationService authenticationService, ObjectRepository objectRepository, ActionService actionService, ModelMapper modelMapper) {
        this.requestRepository = requestRepository;
        this.authenticationService = authenticationService;
        this.objectRepository = objectRepository;
        this.actionService = actionService;
        this.modelMapper = modelMapper;
    }

    @Override
    public RequestView createRequest(String token, RequestPost requestPost) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
        Request request = modelMapper.map(requestPost, Request.class);
        request.setManager((Manager) currentUser);
        request.setActionStatus(actionService.getInitialStatus());
        request.setObject(objectRepository.getById(requestPost.getObjectId()));
        return modelMapper.map(requestRepository.save(request), RequestView.class);
    }

    @Override
    public RequestView getPatchedRequest(String token, Long requestId, ActionPatch actionPatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getEndDate() != null)
            throw new BadRequestException("Cannot update progress in already finished request");
        if (!currentUser.equals(request.getManager()))
            throw new ForbiddenAccessException("Only manager that created request can update its progress");
        modelMapper.map(actionPatch, request);
        actionService.patchAction(actionPatch, request);
        return modelMapper.map(requestRepository.save(request), RequestView.class);
    }

    @Override
    public RequestView getPatchedRequest(String token, Long requestId, RequestPatch requestPatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (!currentUser.equals(request.getManager()))
            throw new ForbiddenAccessException("Only manager that created request can update it");
        modelMapper.map(request, requestPatch);
        return modelMapper.map(requestRepository.save(request), RequestView.class);
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
    public List<RequestView> getRequests(String token, Long objectId) {
        User currentUser = authenticationService.getUserFromToken(token);
        Object object = objectRepository.getById(objectId);
        List<Request> requests = null;
        if (currentUser instanceof Manager)
            requests = requestRepository.findAllByManagerAndObject((Manager) currentUser, object);
        if (currentUser instanceof Client) {
            if (!object.getClient().equals(currentUser))
                throw new ForbiddenAccessException("Client can browse only objects assigned to his account");
            requests = object.getRequests();
        }
        if (currentUser instanceof Worker)
            requests = requestRepository.findAllByActivitiesContainsAndObject(((Worker) currentUser).getActivities(), object);
        if (requests == null)
            throw new ForbiddenAccessException(Manager.class, Worker.class, Client.class);
        return requests.stream().map(request -> modelMapper.map(request, RequestView.class)).collect(Collectors.toList());
    }

    @Override
    public List<RequestView> getRequests(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        List<Request> requests = null;
        if (currentUser instanceof Client)
            requests = requestRepository.findAllByObjectClient((Client) currentUser);
        if (currentUser instanceof Manager)
            requests = ((Manager) currentUser).getRequests();
        if (currentUser instanceof Worker)
            requests = requestRepository.findAllByActivitiesContains(((Worker) currentUser).getActivities());
        if (requests == null)
            throw new ForbiddenAccessException(Manager.class, Worker.class, Client.class);
        return requests.stream().map(request -> modelMapper.map(request, RequestView.class)).collect(Collectors.toList());
    }

}
