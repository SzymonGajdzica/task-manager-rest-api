package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Activity;
import pl.polsl.task.manager.rest.api.models.Request;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.models.Worker;
import pl.polsl.task.manager.rest.api.repositories.ActivityRepository;
import pl.polsl.task.manager.rest.api.repositories.ActivityTypeRepository;
import pl.polsl.task.manager.rest.api.repositories.RequestRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPatch;
import pl.polsl.task.manager.rest.api.views.ActivityPost;
import pl.polsl.task.manager.rest.api.views.ActivityView;

import java.util.List;

@Component
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final AuthenticationService authenticationService;
    private final ActionService actionService;

    public ActivityServiceImpl(ActivityRepository activityRepository, RequestRepository requestRepository, UserRepository userRepository, ActivityTypeRepository activityTypeRepository, AuthenticationService authenticationService, ActionService actionService) {
        this.activityRepository = activityRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.authenticationService = authenticationService;
        this.actionService = actionService;
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
        activity.setActionStatus(actionService.getInitialStatus());
        return activityRepository.save(activity);
    }

    @Override
    public Activity getPatchedActivity(String token, Long activityId, ActionPatch actionPatch) {
        User user = authenticationService.getUserFromToken(token);
        Activity activity = activityRepository.getById(activityId);
        if (activity.getResult() != null)
            throw new BadRequestException("Cannot update progress in already finished activity");
        if (activity.getWorker() != user || activity.getRequest().getManager() != user)
            throw new ForbiddenAccessException("Only worker assigned to activity and manager that created it can update its progress");
        return activityRepository.save(actionService.getPatchedAction(activity, actionPatch));
    }

    @Override
    public Activity getPatchedActivity(String token, Long activityId, ActivityPatch activityPatch) {
        User manager = authenticationService.getUserFromToken(token);
        Activity activity = activityRepository.getById(activityId);
        if (activity.getRequest().getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can update its parameters");
        updateActivityTypeAndWorker(activity, activityPatch.getActivityTypeCode(), activityPatch.getWorkerId());
        if (activityPatch.getDescription() != null)
            activity.setDescription(activityPatch.getDescription());
        return activityRepository.save(activity);
    }

    @Override
    public List<Activity> getRequestActivities(String token, Long requestId) {
        User manager = authenticationService.getUserFromToken(token);
        Request request = requestRepository.getById(requestId);
        if (request.getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can retrieve its activities");
        return request.getActivities();
    }

    @Override
    public List<Activity> getWorkerActivities(String token) {
        User worker = authenticationService.getUserFromToken(token);
        if (!(worker instanceof Worker))
            throw new BadRequestException("This request is designed for workers");
        return activityRepository.findAllByWorkerId(worker.getId());
    }

    @Override
    public void deleteActivity(String token, Long activityId) {
        User manager = authenticationService.getUserFromToken(token);
        Activity activity = activityRepository.getById(activityId);
        if (activity.getRequest().getManager() != manager)
            throw new ForbiddenAccessException("Only manager that created request can remove it");
        activityRepository.delete(activity);
    }

    private void updateActivityTypeAndWorker(Activity activity, String activityTypeCode, Long workerId) {
        if (activityTypeCode != null)
            activity.setActivityType(activityTypeRepository.getById(activityTypeCode));
        if (workerId != null) {
            User worker = userRepository.getById(workerId);
            if (!(worker instanceof Worker))
                throw new BadRequestException("WorkerId does not point at worker");
            activity.setWorker((Worker) worker);
        }
    }

    @Override
    public ActivityView serialize(Activity activity) {
        ActivityView activityView = actionService.serialize(activity, new ActivityView());
        activityView.setStatusCode(activity.getActionStatus().getCode());
        if (activity.getWorker() != null)
            activityView.setWorkerId(activity.getWorker().getId());
        if (activity.getActivityType() != null)
            activityView.setActivityCode(activity.getActivityType().getCode());
        return activityView;
    }
}
