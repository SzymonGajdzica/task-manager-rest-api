package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserView;

import java.util.List;

public interface UserService {

    @NonNull
    UserView createUser(String token, UserPost userPost);

    @NonNull
    UserView getUser(String token);

    @NonNull
    UserView getPatchedUser(String token, Long userId, UserPatch userPatch);

    @NonNull
    List<UserView> getUsers();

}
