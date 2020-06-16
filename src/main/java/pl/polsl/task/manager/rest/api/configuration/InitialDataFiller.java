package pl.polsl.task.manager.rest.api.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import pl.polsl.task.manager.rest.api.services.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class InitialDataFiller implements ApplicationRunner {

    private final List<StartUpFiller> startUpFillers = new LinkedList<>();

    public InitialDataFiller(UserServiceImpl userService, RoleServiceImpl roleService, ActionStatusServiceImpl actionStatusService, ObjectTypeServiceImpl objectTypeService, ActivityTypeServiceImpl activityTypeService) {
        startUpFillers.addAll(Arrays.asList(userService, roleService, actionStatusService, activityTypeService, objectTypeService));
    }

    @Override
    public void run(ApplicationArguments args) {
        for(StartUpFiller startUpFiller: startUpFillers) {
            try {
                startUpFiller.createInitialData();
            } catch (Exception ignore) {

            }
        }
    }

}
