package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
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

    public RequestServiceImpl(RequestRepository requestRepository, StatusRepository statusRepository) {
        this.requestRepository = requestRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Request createRequest(User user, RequestPost requestPost) {
        if(!(user instanceof Manager))
            throw new ForbiddenAccessException("Only manager can create request");
        Request request = new Request();
        request.setManager((Manager) user);
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
    public List<Request> getAllRequests(User user) {
        if(!(user instanceof Manager))
            throw new ForbiddenAccessException("Only manager can browse requests");
        return requestRepository.findAllByManagerId(user.getId());
    }

}
