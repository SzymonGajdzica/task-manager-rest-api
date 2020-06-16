package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;

public interface RoleService {

    @NonNull
    List<CodeNameView> getRoles();

}
