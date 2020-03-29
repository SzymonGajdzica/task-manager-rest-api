package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.Arrays;
import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void createInitialData() throws Exception {
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

    @Override
    public CodeNameView serialize(Role role) {
        CodeNameView codeNameView = new CodeNameView();
        codeNameView.setCode(role.getCode());
        codeNameView.setName(role.getName());
        return codeNameView;
    }
}
