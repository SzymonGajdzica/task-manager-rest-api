package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;

public interface UserService {

    @NonNull
    User getUser(String token);

    @NonNull
    User getPatchedUser(String token, UserPatch userPatch);

    @NonNull
    User getUserWithPatchedRole(String token, Long userId, UserRolePatch userRolePatch);

    void deleteUser(String token);

    @NonNull
    UserView serialize(User user);

}
