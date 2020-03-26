package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.BadRequestException;
import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.models.Status;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;
import pl.polsl.task.manager.rest.api.views.ActionPatch;

import java.util.Date;

@Component
public class ActionServiceImpl implements ActionService {

    private final StatusRepository statusRepository;

    public ActionServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public <T extends Action> T getPatchAction(T action, ActionPatch actionPatch) {
        String statusCode = actionPatch.getStatusCode();
        if (statusCode != null) {
            Status status = statusRepository.getById(statusCode);
            if (!action.getStatus().getChildStatuses().contains(status))
                throw new BadRequestException("Status broke requested flow");
            action.setStatus(status);
            if (status.getChildStatuses().isEmpty()) {
                action.setResult(action.getResult());
                action.setEndDate(new Date());
            }
        }
        return action;
    }

    @Override
    public Status getInitialStatus() {
        return statusRepository.findAll()
                .stream()
                .filter(status -> status.getParentStatuses().isEmpty())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Not found initial status"));
    }


}
