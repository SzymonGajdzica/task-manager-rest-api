package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.WrongRequestBodyException;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.models.Worker;
import pl.polsl.task.manager.rest.api.repositories.*;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;

@Component
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final StatusRepository statusRepository;

    public ActivityServiceImpl(ActivityRepository activityRepository, RequestRepository requestRepository, UserRepository userRepository, ActivityTypeRepository activityTypeRepository, StatusRepository statusRepository) {
        this.activityRepository = activityRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Activity createActivity(User manager, ActivityPost activityPost) {
        Request request = requestRepository.getOne(activityPost.getRequestId());
        if(request.getManager() != manager)
            throw new ForbiddenAccessException("only that created request can add activities to it");
        Activity activity = new Activity();
        if(activityPost.getWorkerId() != null){
            User worker = userRepository.getOne(activityPost.getWorkerId());
            if(!(worker instanceof Worker))
                throw new WrongRequestBodyException("workerId does not point at worker");
            activity.setWorker((Worker) worker);
        }
        activity.setRequest(request);
        activity.setActivityType(activityTypeRepository.getOne(activityPost.getActivityCode()));
        activity.setDescription(activityPost.getDescription());
        activity.setStatus(statusRepository.getOne("OPN"));
        return activityRepository.save(activity);
    }

    @Override
    public ActivityView serialize(Activity activity) {
        ActivityView activityView = new ActivityView();
        activityView.setId(activity.getId());
        activityView.setDescription(activity.getDescription());
        activityView.setEndDate(activity.getEndDate());
        activityView.setRequestId(activity.getRequest().getId());
        activityView.setRegisterDate(activity.getRegisterDate());
        activityView.setResult(activity.getResult());
        activityView.setStatusCode(activity.getStatus().getCode());
        if (activity.getWorker() != null)
            activityView.setWorkerId(activity.getWorker().getId());
        activityView.setActivityCode(activity.getActivityType().getCode());
        return activityView;
    }
}
