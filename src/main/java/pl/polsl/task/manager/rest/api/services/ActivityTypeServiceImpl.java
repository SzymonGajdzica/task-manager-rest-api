package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.CodeAlreadyUsedException;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.ActivityTypeRepository;
import pl.polsl.task.manager.rest.api.views.ActivityTypePatch;
import pl.polsl.task.manager.rest.api.views.ActivityTypePost;
import pl.polsl.task.manager.rest.api.views.ActivityTypeView;

import java.util.List;

@Component
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;
    private final AuthenticationService authenticationService;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository, AuthenticationService authenticationService) {
        this.activityTypeRepository = activityTypeRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public ActivityType createActivityType(String token, ActivityTypePost activityTypePost) {
        User currentUser = authenticationService.getUserFromToken(token);
        if(!(currentUser instanceof Admin || currentUser instanceof Manager))
            throw new ForbiddenAccessException(Manager.class, Admin.class);
        if(activityTypeRepository.existsById(activityTypePost.getCode()))
            throw new CodeAlreadyUsedException(activityTypePost.getCode());
        ActivityType activityType = new ActivityType();
        activityType.setCode(activityTypePost.getCode());
        activityType.setName(activityTypePost.getName());
        return activityTypeRepository.save(activityType);
    }

    @Override
    public ActivityType getPatchedActivity(String token, String activityTypeCode, ActivityTypePatch activityTypePatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        if(!(currentUser instanceof Admin || currentUser instanceof Manager))
            throw new ForbiddenAccessException(Manager.class, Admin.class);
        ActivityType activityType = activityTypeRepository.findById(activityTypeCode)
                .orElseThrow(() -> new NotFoundException(ActivityType.class, activityTypeCode));
        if(activityTypePatch.getName() != null)
            activityType.setName(activityTypePatch.getName());
        return activityTypeRepository.save(activityType);
    }

    @Override
    public List<ActivityType> getAllActivityTypes(String token) {
        User currentUser = authenticationService.getUserFromToken(token);
        if(!(currentUser instanceof Admin || currentUser instanceof Manager || currentUser instanceof Worker))
            throw new ForbiddenAccessException(Manager.class, Admin.class, Worker.class);
        return activityTypeRepository.findAll();
    }

    @Override
    public ActivityTypeView serialize(ActivityType activityType) {
        ActivityTypeView activityTypeView = new ActivityTypeView();
        activityTypeView.setCode(activityType.getCode());
        activityTypeView.setName(activityType.getName());
        return activityTypeView;
    }
}
