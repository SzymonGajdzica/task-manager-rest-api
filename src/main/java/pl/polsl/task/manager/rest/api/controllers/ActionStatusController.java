package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.services.ActionStatusService;
import pl.polsl.task.manager.rest.api.views.ActionStatusView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/action_status")
public class ActionStatusController {

    private final ActionStatusService actionStatusService;

    public ActionStatusController(ActionStatusService actionStatusService) {
        this.actionStatusService = actionStatusService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActionStatusView> getRequestActivities() {
        List<ActionStatus> actionStatuses = actionStatusService.getAvailableStatuses();
        return actionStatuses.stream().map(actionStatusService::serialize).collect(Collectors.toList());
    }


}
