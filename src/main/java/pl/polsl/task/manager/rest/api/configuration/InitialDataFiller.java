package pl.polsl.task.manager.rest.api.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import pl.polsl.task.manager.rest.api.models.ActionStatus;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

@Configuration
public class InitialDataFiller implements ApplicationRunner {

    private final StatusRepository statusRepository;

    public InitialDataFiller(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (statusRepository.count() != 0)
            return;

        ActionStatus actionStatusOpen = new ActionStatus();
        actionStatusOpen.setCode("OPN");
        actionStatusOpen.setName("Open");

        ActionStatus actionStatusProgress = new ActionStatus();
        actionStatusProgress.setCode("PRO");
        actionStatusProgress.setName("Progress");

        ActionStatus actionStatusCancelled = new ActionStatus();
        actionStatusCancelled.setCode("CAN");
        actionStatusCancelled.setName("Canceled");

        ActionStatus actionStatusFinished = new ActionStatus();
        actionStatusFinished.setCode("FIN");
        actionStatusFinished.setName("Finished");

        statusRepository.saveAll(Arrays.asList(actionStatusFinished, actionStatusCancelled, actionStatusProgress, actionStatusOpen));

        actionStatusOpen.setChildActionStatuses(new HashSet<>(Arrays.asList(actionStatusCancelled, actionStatusFinished, actionStatusProgress)));
        actionStatusProgress.setChildActionStatuses(new HashSet<>(Arrays.asList(actionStatusCancelled, actionStatusFinished)));
        actionStatusProgress.setParentActionStatuses(new HashSet<>(Collections.singletonList(actionStatusOpen)));
        actionStatusCancelled.setParentActionStatuses(new HashSet<>(Arrays.asList(actionStatusOpen, actionStatusProgress)));
        actionStatusFinished.setParentActionStatuses(new HashSet<>(Arrays.asList(actionStatusOpen, actionStatusProgress)));

        statusRepository.saveAll(Arrays.asList(actionStatusFinished, actionStatusCancelled, actionStatusProgress, actionStatusOpen));
    }

}
