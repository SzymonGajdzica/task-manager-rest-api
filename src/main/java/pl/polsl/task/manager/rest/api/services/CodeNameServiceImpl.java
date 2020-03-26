package pl.polsl.task.manager.rest.api.services;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.ForbiddenAccessException;
import pl.polsl.task.manager.rest.api.models.Admin;
import pl.polsl.task.manager.rest.api.models.CodeName;
import pl.polsl.task.manager.rest.api.models.Manager;
import pl.polsl.task.manager.rest.api.models.User;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

@Component
public class CodeNameServiceImpl implements CodeNameService {

    private final AuthenticationService authenticationService;

    public CodeNameServiceImpl(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Override
    public void validateIfUserCanModify(String token) {
        User user = authenticationService.getUserFromToken(token);
        if (!(user instanceof Admin || user instanceof Manager))
            throw new ForbiddenAccessException(Manager.class, Admin.class);
    }

    @Override
    public <T extends CodeName> T getPatchedCodeName(T codeName, CodeNamePatch codeNamePatch) {
        if (codeNamePatch.getName() != null)
            codeName.setName(codeNamePatch.getName());
        return codeName;
    }

    @Override
    public <T extends CodeName> T getPatchedCodeName(T codeName, CodeNamePost codeNamePost) {
        codeName.setCode(codeNamePost.getCode());
        codeName.setName(codeNamePost.getName());
        return codeName;
    }

    @Override
    public CodeNameView serialize(CodeName codeName) {
        CodeNameView codeNameView = new CodeNameView();
        codeNameView.setCode(codeName.getCode());
        codeNameView.setName(codeName.getName());
        return codeNameView;
    }

}
