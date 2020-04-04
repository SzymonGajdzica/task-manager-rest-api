package pl.polsl.task.manager.rest.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.*;
import pl.polsl.task.manager.rest.api.repositories.RoleRepository;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CodeNameView> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().map(role -> modelMapper.map(role, CodeNameView.class)).collect(Collectors.toList());
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

}
