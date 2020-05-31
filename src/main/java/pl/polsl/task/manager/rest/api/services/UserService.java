package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;

import java.util.List;

public interface UserService {

    @NonNull
    UserView createUser(String token, UserPost userPost);

    @NonNull
    UserView getUser(String token);

    @NonNull
    UserView getPatchedUser(String token, UserPatch userPatch);

    @NonNull
    UserView getUserWithPatchedRole(String token, Long userId, UserRolePatch userRolePatch);

    @NonNull
    List<UserView> getUsers();

    void createInitialData() throws RuntimeException;

}
