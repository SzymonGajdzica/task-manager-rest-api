package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserView;

public interface UserMapper {

    @NonNull
    User map(UserPost userPost);

    void map(UserPatch userPatch, User user);

    UserView map(User user);

}
