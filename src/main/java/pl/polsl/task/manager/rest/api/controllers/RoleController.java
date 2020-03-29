package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.polsl.task.manager.rest.api.models.Role;
import pl.polsl.task.manager.rest.api.services.RoleService;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeNameView> getRoles() {
        List<Role> roles = roleService.getRoles();
        return roles.stream().map(roleService::serialize).collect(Collectors.toList());
    }


}
