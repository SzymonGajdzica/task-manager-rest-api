package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.AuthenticationPost;
import pl.polsl.task.manager.rest.api.views.AuthenticationView;

public interface AuthenticationService {

    @NonNull
    User getUserFromToken(String token);

    @NonNull
    AuthenticationView loginUser(AuthenticationPost authenticationPost);

}
