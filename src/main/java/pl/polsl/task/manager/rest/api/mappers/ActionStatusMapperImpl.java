package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.models.CodeEntity;
import pl.polsl.task.manager.rest.api.views.ActionStatusView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ActionStatusMapperImpl implements ActionStatusMapper {

    @Override
    public ActionStatusView map(ActionStatus actionStatus) {
        ActionStatusView actionStatusView = new ActionStatusView();
        actionStatusView.setCode(actionStatus.getCode());
        actionStatusView.setName(actionStatus.getName());
        List<String> childrenCodes = actionStatus
                .getChildActionStatuses()
                .stream()
                .map(CodeEntity::getCode)
                .collect(Collectors.toList());
        actionStatusView.setChildrenCodes(childrenCodes);
        return actionStatusView;
    }

}
