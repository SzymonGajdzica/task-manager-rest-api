package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActionView;

public interface ActionService {

    @NonNull
    <T extends Action> T getPatchedAction(T action, ActionPatch actionPatch);

    @NonNull
    ActionStatus getInitialStatus();

    @NonNull
    <T extends ActionView> T serialize(Action action, T actionView);

}
