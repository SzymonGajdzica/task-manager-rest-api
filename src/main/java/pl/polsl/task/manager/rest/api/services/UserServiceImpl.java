package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Admin;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserRolePatch;
import pl.polsl.task.manager.rest.api.views.UserView;

@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final RoleClassNameFactory roleClassNameFactory;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, RoleRepository roleRepository, RoleClassNameFactory roleClassNameFactory) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
        this.roleClassNameFactory = roleClassNameFactory;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getOne(id);
    }

    @Override
    public User getUser(String token) {
        return authenticationService.getUserFromHeader(token);
    }

    @Override
    public User getPatchedUser(User user, UserPatch userPatch) {
        if(userPatch.getName() != null)
            user.setName(userPatch.getName());
        if(userPatch.getSurname() != null)
            user.setSurname(userPatch.getSurname());
        if(userPatch.getEmail() != null)
            user.setEmail(userPatch.getEmail());
        return user;
    }

    @Override
    public User getUserWithPatchedRole(User currentUser, User user, UserRolePatch userRolePatch) {
        if (!(currentUser instanceof Admin))
            throw new ForbiddenAccessException("Only administrator can change roles");
        if (user instanceof Admin)
            throw new ForbiddenAccessException("Cannot change role of other administrator");
        if (userRolePatch.getRoleCode() != null)
            user.setRole(roleRepository.getOne(userRolePatch.getRoleCode()));
        else
            user.setRole(null);
        userRepository.updateRole(user.getId(), roleClassNameFactory.getClassName(userRolePatch.getRoleCode()));
        return userRepository.save(user);
    }

    @Override
    public void removeUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserView serialize(User user) {
        UserView userView = new UserView();
        userView.setId(user.getId());
        userView.setName(user.getName());
        userView.setEmail(user.getEmail());
        userView.setSurname(user.getSurname());
        userView.setUsername(user.getUsername());
        if(user.getRole() != null)
            userView.setRoleCode(user.getRole().getCode());
        return userView;
    }
}
