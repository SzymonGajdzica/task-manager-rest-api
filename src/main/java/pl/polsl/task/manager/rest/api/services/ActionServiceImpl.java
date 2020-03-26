package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActionView;

import java.util.Date;

@Component
public class ActionServiceImpl implements ActionService {

    private final StatusRepository statusRepository;

    public ActionServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public <T extends Action> T getPatchedAction(T action, ActionPatch actionPatch) {
        String statusCode = actionPatch.getStatusCode();
        if (statusCode != null) {
            ActionStatus actionStatus = statusRepository.getById(statusCode);
            if (!action.getActionStatus().getChildActionStatuses().contains(actionStatus))
                throw new BadRequestException("Status broke requested flow");
            action.setActionStatus(actionStatus);
            if (actionStatus.getChildActionStatuses().isEmpty()) {
                action.setResult(action.getResult());
                action.setEndDate(new Date());
            }
        }
        return action;
    }

    @Override
    public ActionStatus getInitialStatus() {
        return statusRepository.findAll()
                .stream()
                .filter(status -> status.getParentActionStatuses().isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found initial status"));
    }

    @Override
    public <T extends ActionView> T serialize(Action action, T actionView) {
        actionView.setId(action.getId());
        actionView.setDescription(action.getDescription());
        actionView.setStatusCode(action.getActionStatus().getCode());
        actionView.setResult(action.getResult());
        actionView.setRegisterDate(action.getRegisterDate());
        actionView.setEndDate(action.getEndDate());
        return actionView;
    }
}
