package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.CodeName;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

public interface CodeNameService {

    void validateIfUserCanModify(String token);

    @NonNull
    <T extends CodeName> T getPatchedCodeName(T codeName, CodeNamePatch codeNamePatch);

    @NonNull
    <T extends CodeName> T getPatchedCodeName(T codeName, CodeNamePost codeNamePost);

    @NonNull
    CodeNameView serialize(CodeName codeName);

}
