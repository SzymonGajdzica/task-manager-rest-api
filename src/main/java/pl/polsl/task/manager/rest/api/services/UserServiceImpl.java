package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.Nullable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.exceptions.NotImplementedException;
import pl.polsl.task.manager.rest.api.exceptions.UsernameAlreadyUsedException;
import pl.polsl.task.manager.rest.api.mappers.UserMapper;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.repositories.UserRepository;
import pl.polsl.task.manager.rest.api.views.UserPatch;
import pl.polsl.task.manager.rest.api.views.UserPost;
import pl.polsl.task.manager.rest.api.views.UserView;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserServiceImpl implements UserService, StartUpFiller {

    private final UserRepository userRepository;
    private final AuthenticationService authenticationService;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, AuthenticationService authenticationService, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.authenticationService = authenticationService;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userMapper = userMapper;
    }

    @Override
    public UserView createUser(String token, UserPost userPost) {
        User currentUser = authenticationService.getUserFromToken(token);
        if (!(currentUser instanceof Admin))
            throw new ForbiddenAccessException(Admin.class);
        if (userRepository.findByUsername(userPost.getUsername()).isPresent())
            throw new UsernameAlreadyUsedException(userPost.getUsername());
        User user = userMapper.map(userPost);
        if (userPost.getRoleCode() != null)
            user.setRole(roleRepository.getById(userPost.getRoleCode()));
        UserView userView = userMapper.map(userRepository.save(user));
        if (userView.getRoleCode() != null)
            userRepository.updateRole(userView.getId(), getClassName(userView.getRoleCode()));
        return userView;
    }

    @Override
    public UserView getUser(String token) {
        return userMapper.map(authenticationService.getUserFromToken(token));
    }

    @Override
    public UserView getPatchedUser(String token, Long userId, UserPatch userPatch) {
        User currentUser = authenticationService.getUserFromToken(token);
        User userToUpdate = userRepository.getById(userId);
        if (!(currentUser instanceof Admin))
            throw new ForbiddenAccessException(Admin.class);
        if (userToUpdate instanceof Admin)
            throw new ForbiddenAccessException("Cannot change other administrator");
        userMapper.map(userPatch, userToUpdate);
        if(userPatch.getPassword() != null)
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(userPatch.getPassword()));
        if(userPatch.getHasRoleCode()) {
            if(userPatch.getRoleCode() != null)
                userToUpdate.setRole(roleRepository.getById(userPatch.getRoleCode()));
            else
                userToUpdate.setRole(null);
            userRepository.updateRole(userToUpdate.getId(), getClassName(userPatch.getRoleCode()));
        }
        return userMapper.map(userRepository.save(userToUpdate));
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
    public List<UserView> getUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::map).collect(Collectors.toList());
    }

    @Override
    public void createInitialData() throws RuntimeException {
        Admin admin = new Admin();
        admin.setEmail("primaryAdmin@wp.pl");
        admin.setName("Primary");
        admin.setSurname("Admin");
        admin.setUsername("admin1");
        admin.setPassword(bCryptPasswordEncoder.encode("admin1"));
        admin.setRole(roleRepository.getOne("ADM"));
        userRepository.save(admin);

        Client client = new Client();
        client.setEmail("client@wp.pl");
        client.setName("Josh");
        client.setSurname("Clint");
        client.setUsername("client1");
        client.setPassword(bCryptPasswordEncoder.encode("client1"));
        client.setRole(roleRepository.getOne("CLI"));
        userRepository.save(client);

        Worker worker = new Worker();
        worker.setEmail("worker@wp.pl");
        worker.setName("Bob");
        worker.setSurname("Worens");
        worker.setUsername("worker1");
        worker.setPassword(bCryptPasswordEncoder.encode("worker1"));
        worker.setRole(roleRepository.getOne("WOR"));
        userRepository.save(worker);

        Manager manager = new Manager();
        manager.setEmail("manager@wp.pl");
        manager.setName("Lucian");
        manager.setSurname("Maners");
        manager.setUsername("manager1");
        manager.setPassword(bCryptPasswordEncoder.encode("manager1"));
        manager.setRole(roleRepository.getOne("MAN"));
        userRepository.save(manager);
    }

}
