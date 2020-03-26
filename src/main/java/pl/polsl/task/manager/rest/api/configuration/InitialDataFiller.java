package pl.polsl.task.manager.rest.api.configuration;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import pl.polsl.task.manager.rest.api.models.Status;
import pl.polsl.task.manager.rest.api.repositories.StatusRepository;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class InitialDataFiller implements ApplicationRunner {

    private final StatusRepository statusRepository;

    public InitialDataFiller(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (!statusRepository.findAll().isEmpty())
            return;

        Status statusOpen = new Status();
        statusOpen.setCode("OPN");
        statusOpen.setName("Open");

        Status statusProgress = new Status();
        statusProgress.setCode("PRO");
        statusProgress.setName("Progress");

        Status statusCancelled = new Status();
        statusCancelled.setCode("CAN");
        statusCancelled.setName("Canceled");

        Status statusFinished = new Status();
        statusFinished.setCode("FIN");
        statusFinished.setName("Finished");

        statusRepository.saveAll(Arrays.asList(statusFinished, statusCancelled, statusProgress, statusOpen));

        statusOpen.setChildStatuses(Arrays.asList(statusCancelled, statusFinished, statusProgress));
        statusProgress.setChildStatuses(Arrays.asList(statusCancelled, statusFinished));
        statusProgress.setParentStatuses(Collections.singletonList(statusOpen));
        statusCancelled.setParentStatuses(Arrays.asList(statusOpen, statusProgress));
        statusFinished.setParentStatuses(Arrays.asList(statusOpen, statusProgress));

        statusRepository.saveAll(Arrays.asList(statusFinished, statusCancelled, statusProgress, statusOpen));
    }

}
