package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.models.CodeEntity;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;
import pl.polsl.task.manager.rest.api.views.ActionStatusView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionStatusServiceImpl implements ActionStatusService {

    private final StatusRepository statusRepository;

    public ActionStatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public List<ActionStatus> getAvailableStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public ActionStatusView serialize(ActionStatus actionStatus) {
        ActionStatusView actionStatusView = new ActionStatusView();
        actionStatusView.setCode(actionStatus.getCode());
        actionStatusView.setName(actionStatus.getCode());
        List<String> childrenCode = actionStatus.getChildActionStatuses()
                .stream()
                .map(CodeEntity::getCode)
                .collect(Collectors.toList());
        actionStatusView.setChildrenCode(childrenCode);
        return actionStatusView;
    }
}
