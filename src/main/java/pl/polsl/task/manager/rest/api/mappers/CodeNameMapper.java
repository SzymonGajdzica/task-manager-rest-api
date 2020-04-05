package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.lang.NonNull;
import pl.polsl.task.manager.rest.api.models.CodeName;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

public interface CodeNameMapper {

    void map(CodeNamePost codeNamePost, CodeName codeName);

    @NonNull
    CodeNameView map(CodeName codeName);

    void map(CodeNamePatch codeNamePatch, CodeName codeName);

}
