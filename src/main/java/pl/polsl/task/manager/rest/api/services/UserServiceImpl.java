package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.NotFoundException;
import pl.polsl.task.manager.rest.api.models.Admin;
import pl.polsl.task.manager.rest.api.models.Role;
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
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(User.class, id));
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
        User userToUpdate = getUser(userId);
        if (!(currentUser instanceof Admin))
            throw new ForbiddenAccessException(Admin.class);
        if (userToUpdate instanceof Admin)
            throw new ForbiddenAccessException("Cannot change role of other administrator");
        if (userRolePatch.getRoleCode() != null) {
            Role role = roleRepository.findById(userRolePatch.getRoleCode())
                    .orElseThrow(() -> new NotFoundException(Role.class, userRolePatch.getRoleCode()));
            userToUpdate.setRole(role);
        } else
            userToUpdate.setRole(null);
        userRepository.updateRole(userToUpdate.getId(), roleClassNameFactory.getClassName(userRolePatch.getRoleCode()));
        return userRepository.save(userToUpdate);
    }

    @Override
    public void removeUser(String token) {
        User currentUser = getUser(token);
        userRepository.delete(currentUser);
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
