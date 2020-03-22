package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;

public interface UserService {

    @NonNull
    User getUser(Long id);

    @NonNull
    User getUser(String token);

    @NonNull
    User getPatchedUser(User user, UserPatch userPatch);

    @NonNull
    User getUserWithPatchedRole(User currentUser, User user, UserRolePatch userRolePatch);

    void removeUser(User user);

    @NonNull
    UserView serialize(User user);

}
