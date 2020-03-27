package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.NotImplementedException;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;

import java.util.List;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
    }

    @Override
    public User getUser(String token) {
        return authenticationService.getUserFromToken(token);
    }

    @Override
    public User getPatchedUser(String token, UserPatch userPatch) {
        User currentUser = getUser(token);
        if(userPatch.getName() != null)
            currentUser.setName(userPatch.getName());
        if(userPatch.getSurname() != null)
            currentUser.setSurname(userPatch.getSurname());
        if(userPatch.getEmail() != null)
            currentUser.setEmail(userPatch.getEmail());
        return currentUser;
    }

    @Override
    public User getUserWithPatchedRole(String token, Long userId, UserRolePatch userRolePatch) {
        User currentUser = getUser(token);
        User userToUpdate = userRepository.getById(userId);
        if (!(currentUser instanceof Admin))
            throw new ForbiddenAccessException(Admin.class);
        if (userToUpdate instanceof Admin)
            throw new ForbiddenAccessException("Cannot change role of other administrator");
        if (userRolePatch.getRoleCode() != null)
            userToUpdate.setRole(roleRepository.getById(userRolePatch.getRoleCode()));
        else
            userToUpdate.setRole(null);
        userRepository.updateRole(userToUpdate.getId(), getClassName(userRolePatch.getRoleCode()));
        return userRepository.save(userToUpdate);
    }

    private String getClassName(@Nullable String roleCode) {
        if (roleCode == null)
            return User.class.getSimpleName();
        switch (roleCode) {
            case "MAN":
                return Manager.class.getSimpleName();
            case "CLI":
                return Client.class.getSimpleName();
            case "ADM":
                return Admin.class.getSimpleName();
            case "WOR":
                return Worker.class.getSimpleName();
            default:
                throw new NotImplementedException("Missing implementation for role with code " + roleCode);
        }
    }

    @Override
    public void deleteUser(String token) {
        User currentUser = getUser(token);
        userRepository.delete(currentUser);
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserView serialize(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setName(user.getName());
        userView.setEmail(user.getEmail());
        userView.setSurname(user.getSurname());
        userView.setUsername(user.getUsername());
        if (user.getRole() != null)
            userView.setRoleCode(user.getRole().getCode());
        return userView;
    }
}
