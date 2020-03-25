package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Admin;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.RequestRepository;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;
import pl.polsl.task.manager.rest.api.views.RequestPost;
import pl.polsl.task.manager.rest.api.views.RequestView;

import java.util.List;

@Component
public class RequestServiceImpl implements RequestService{

    private final RequestRepository requestRepository;
    private final StatusRepository statusRepository;
    private final AuthenticationService authenticationService;

    public RequestServiceImpl(RequestRepository requestRepository, StatusRepository statusRepository, AuthenticationService authenticationService) {
        this.requestRepository = requestRepository;
        this.statusRepository = statusRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Request createRequest(String token, RequestPost requestPost) {
        User currentUser = authenticationService.getUserFromToken(token);
        if(!(currentUser instanceof Manager))
            throw new ForbiddenAccessException(Manager.class);
        Request request = new Request();
        request.setManager((Manager) currentUser);
        request.setDescription(requestPost.getDescription());
        request.setStatus(statusRepository.getOne("OPN"));
        return requestRepository.save(request);
    }

    @Override
    public RequestView serialize(Request request) {
        RequestView requestView = new RequestView();
        requestView.setId(request.getId());
        requestView.setObjectId(request.getObject().getId());
        requestView.setDescription(request.getDescription());
        requestView.setStatusCode(request.getStatus().getCode());
        requestView.setResult(request.getResult());
        requestView.setRegisterDate(request.getRegisterDate());
        requestView.setEndDate(request.getEndDate());
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
