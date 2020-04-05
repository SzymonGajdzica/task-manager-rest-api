package pl.polsl.task.manager.rest.api.mappers;

import pl.polsl.task.manager.rest.api.models.Action;
import pl.polsl.task.manager.rest.api.views.ActionPatch;
import pl.polsl.task.manager.rest.api.views.ActionPost;
import pl.polsl.task.manager.rest.api.views.ActionView;

public interface ActionMapper {

    <T extends Action, R extends ActionPost> void map(R actionPost, T action);

    <T extends Action, R extends ActionPatch> void map(R actionPatch, T action);

    <T extends ActionView, R extends Action> void map(R action, T actionView);

}
