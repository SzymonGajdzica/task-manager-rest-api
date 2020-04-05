package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.views.ActionStatusView;

public interface ActionStatusMapper {

    @NonNull
    ActionStatusView map(ActionStatus actionStatus);

}
