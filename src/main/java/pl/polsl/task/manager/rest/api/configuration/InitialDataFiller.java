package pl.polsl.task.manager.rest.api.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import pl.polsl.task.manager.rest.api.services.ActionStatusService;
import pl.polsl.task.manager.rest.api.services.RoleService;
import pl.polsl.task.manager.rest.api.services.UserService;

@Configuration
public class InitialDataFiller implements ApplicationRunner {

    private final UserService userService;
    private final RoleService roleService;
    private final ActionStatusService actionStatusService;

    public InitialDataFiller(UserService userService, RoleService roleService, ActionStatusService actionStatusService) {
        this.userService = userService;
        this.roleService = roleService;
        this.actionStatusService = actionStatusService;
    }

    @Override
    public void run(ApplicationArguments args) {
        try {
            roleService.createInitialData();
        } catch (Exception ignore) {
        }
        try {
            userService.createInitialData();
        } catch (Exception ignore) {
        }
        try {
            actionStatusService.createInitialData();
        } catch (Exception ignore) {
        }

    }

}
