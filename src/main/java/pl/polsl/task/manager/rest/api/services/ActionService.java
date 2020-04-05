package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.views.ActionProgressPatch;

public interface ActionService {

    @NonNull
    <T extends Action> void patchAction(ActionProgressPatch actionProgressPatch, T action);

    @NonNull
    ActionStatus getInitialStatus();

}
