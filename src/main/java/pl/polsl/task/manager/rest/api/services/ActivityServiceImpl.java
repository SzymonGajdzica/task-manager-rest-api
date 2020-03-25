package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.WrongRequestException;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.models.Worker;
import pl.polsl.task.manager.rest.api.repositories.*;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityProgressPatch;
import pl.polsl.task.manager.rest.api.views.ActivityView;

import java.util.Date;

@Component
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final StatusRepository statusRepository;
    private final AuthenticationService authenticationService;

    public ActivityServiceImpl(ActivityRepository activityRepository, RequestRepository requestRepository, UserRepository userRepository, ActivityTypeRepository activityTypeRepository, StatusRepository statusRepository, AuthenticationService authenticationService) {
        this.activityRepository = activityRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.statusRepository = statusRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public Activity createActivity(String token, ActivityPost activityPost) {
        User manager = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(activityPost.getRequestId());
        if(request.getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can add activities to it");
        Activity activity = new Activity();
        updateActivityTypeAndWorker(activity, activityPost.getActivityTypeCode(), activityPost.getWorkerId());
        activity.setRequest(request);
        activity.setDescription(activityPost.getDescription());
        activity.setStatus(statusRepository.getOne("OPN"));
        return activityRepository.save(activity);
    }

    @Override
    public Activity getPatchedActivityProgress(String token, Long activityId, ActivityProgressPatch activityProgressPatch) {
        User worker = authenticationService.getUserFromToken(token);
        Activity activity = activityRepository.getById(activityId);
        if(activity.getWorker() != worker)
            throw new ForbiddenAccessException("Only worker assigned to this activity can update its progress");
        String statusCode = activityProgressPatch.getStatusCode();
        String result = activityProgressPatch.getResult();
        if(statusCode != null)
            activity.setStatus(statusRepository.getById(statusCode));
        if(result != null){
            activity.setResult(result);
            activity.setEndDate(new Date());
        }
        return activityRepository.save(activity);
    }

    @Override
    public Activity getPatchedActivity(String token, Long activityId, ActivityPatch activityPatch) {
        User manager = authenticationService.getUserFromToken(token);
        Activity activity = activityRepository.getById(activityId);
        if(activity.getRequest().getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can update its parameters");
        updateActivityTypeAndWorker(activity, activityPatch.getActivityTypeCode(), activityPatch.getWorkerId());
        if(activityPatch.getDescription() != null)
            activity.setDescription(activityPatch.getDescription());
        return activityRepository.save(activity);
    }

    private void updateActivityTypeAndWorker(Activity activity, String activityTypeCode, Long workerId) {
        if(activityTypeCode != null)
            activity.setActivityType(activityTypeRepository.getById(activityTypeCode));
        if(workerId != null){
            User worker = userRepository.getById(workerId);
            if(!(worker instanceof Worker))
                throw new WrongRequestException("WorkerId does not point at worker");
            activity.setWorker((Worker) worker);
        }
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
        if(activity.getActivityType() != null)
            activityView.setActivityCode(activity.getActivityType().getCode());
        return activityView;
    }
}
