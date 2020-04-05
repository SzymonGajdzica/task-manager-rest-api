package pl.polsl.task.manager.rest.api.mappers;

import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.models.CodeName;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;

@Component
public class CodeNameMapperImpl implements CodeNameMapper {

    @Override
    public void map(CodeNamePost codeNamePost, CodeName codeName) {
        codeName.setName(codeNamePost.getName());
        codeName.setCode(codeNamePost.getCode());
    }

    @Override
    public CodeNameView map(CodeName codeName) {
        CodeNameView codeNameView = new CodeNameView();
        codeNameView.setName(codeName.getName());
        codeNameView.setCode(codeName.getCode());
        return codeNameView;
    }

    @Override
    public void map(CodeNamePatch codeNamePatch, CodeName codeName) {
        if (codeNamePatch.getName() != null)
            codeName.setName(codeNamePatch.getName());
    }

}
