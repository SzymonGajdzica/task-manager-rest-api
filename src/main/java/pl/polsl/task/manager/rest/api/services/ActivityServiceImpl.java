package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.exceptions.WrongRequestException;
import pl.polsl.task.manager.rest.api.models.*;
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
        Request request = requestRepository.findById(activityPost.getRequestId())
                .orElseThrow(() -> new NotFoundException(Request.class, activityPost.getRequestId()));
        if(request.getManager() != manager)
            throw new ForbiddenAccessException("Only that created request can add activities to it");
        ActivityType activityType = activityTypeRepository.findById(activityPost.getActivityCode())
                .orElseThrow(() -> new NotFoundException(ActivityType.class, activityPost.getActivityCode()));
        Activity activity = new Activity();
        if(activityPost.getWorkerId() != null){
            User worker = userRepository.findById(activityPost.getWorkerId())
                    .orElseThrow(() -> new NotFoundException(Worker.class, activityPost.getWorkerId()));
            if(!(worker instanceof Worker))
                throw new WrongRequestException("WorkerId does not point at worker");
            activity.setWorker((Worker) worker);
        }
        activity.setRequest(request);
        activity.setActivityType(activityType);
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
