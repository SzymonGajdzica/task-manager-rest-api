package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActionPost;
import pl.polsl.task.manager.rest.api.views.ActionView;

@Component
public class ActionMapperImpl implements ActionMapper {

    @Override
    public <T extends Action, R extends ActionPost> void map(R actionPost, T action) {
        action.setDescription(actionPost.getDescription());
    }

    @Override
    public <T extends Action, R extends ActionPatch> void map(R actionPatch, T action) {
        if (actionPatch.getDescription() != null)
            action.setDescription(actionPatch.getDescription());
    }

    @Override
    public <T extends ActionView, R extends Action> void map(R action, T actionView) {
        actionView.setDescription(action.getDescription());
        actionView.setEndDate(action.getEndDate());
        actionView.setRegisterDate(action.getRegisterDate());
        actionView.setId(action.getId());
        actionView.setResult(action.getResult());
        actionView.setStatusCode(action.getActionStatus().getCode());
    }

}
