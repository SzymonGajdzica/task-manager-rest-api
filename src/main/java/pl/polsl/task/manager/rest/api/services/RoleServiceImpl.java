package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.mappers.CodeNameMapper;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleServiceImpl implements RoleService, StartUpFiller {

    private final RoleRepository roleRepository;
    private final CodeNameMapper codeNameMapper;

    public RoleServiceImpl(RoleRepository roleRepository, CodeNameMapper codeNameMapper) {
        this.roleRepository = roleRepository;
        this.codeNameMapper = codeNameMapper;
    }

    @Override
    public List<CodeNameView> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(codeNameMapper::map).collect(Collectors.toList());
    }

    @Override
    public void createInitialData() throws RuntimeException {
        Role adminRole = new Role();
        adminRole.setCode("ADM");
        adminRole.setName(Admin.class.getSimpleName());

        Role clientRole = new Role();
        clientRole.setCode("CLI");
        clientRole.setName(Client.class.getSimpleName());

        Role managerRole = new Role();
        managerRole.setCode("MAN");
        managerRole.setName(Manager.class.getSimpleName());

        Role workerRole = new Role();
        workerRole.setCode("WOR");
        workerRole.setName(Worker.class.getSimpleName());

        roleRepository.saveAll(Arrays.asList(adminRole, clientRole, managerRole, workerRole));
    }

}
