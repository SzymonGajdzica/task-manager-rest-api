package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.views.ActionStatusView;

import java.util.List;

public interface ActionStatusService {

    @NonNull
    List<ActionStatus> getAvailableStatuses();

    void createInitialData() throws Exception;

    @NonNull
    ActionStatusView serialize(ActionStatus actionStatus);


}
