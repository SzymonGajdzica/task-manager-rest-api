package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.services.ObjectTypeService;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@RestController
@RequestMapping(value = "/object_type")
public class ObjectTypeController {

    private final ObjectTypeService objectTypeService;

    public ObjectTypeController(ObjectTypeService objectTypeService) {
        this.objectTypeService = objectTypeService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView createObjectType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @RequestBody CodeNamePost codeNamePost) {
        return objectTypeService.createObjectType(token, codeNamePost);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeNameView> getObjectTypes(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        return objectTypeService.getObjectsTypes(token);
    }

    @PatchMapping(value = "/{activityTypeCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView updateObjectType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @PathVariable String activityTypeCode,
                                         @RequestBody CodeNamePatch codeNamePatch) {
        return objectTypeService.getPatchedObjectType(token, activityTypeCode, codeNamePatch);
    }

}
