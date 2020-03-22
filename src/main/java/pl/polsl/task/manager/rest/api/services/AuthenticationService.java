package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.AuthenticationPost;
import pl.polsl.task.manager.rest.api.views.AuthenticationView;
import pl.polsl.task.manager.rest.api.views.UserPost;

public interface AuthenticationService {

    @NonNull
    User getUserFromHeader(String token);

    @NonNull
    User registerUser(UserPost userPost);

    @NonNull
    AuthenticationView loginUser(AuthenticationPost authenticationPost);

}
