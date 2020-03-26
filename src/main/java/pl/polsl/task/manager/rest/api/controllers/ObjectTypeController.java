package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.ObjectType;
import pl.polsl.task.manager.rest.api.services.ObjectTypeService;
import pl.polsl.task.manager.rest.api.views.CodeNamePatch;
import pl.polsl.task.manager.rest.api.views.CodeNamePost;
import pl.polsl.task.manager.rest.api.views.CodeNameView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/object_type")
public class ObjectTypeController {

    private final ObjectTypeService objectTypeService;

    public ObjectTypeController(ObjectTypeService objectTypeService) {
        this.objectTypeService = objectTypeService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView createObjectType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @RequestBody CodeNamePost codeNamePost) {
        ObjectType objectType = objectTypeService.createObjectType(token, codeNamePost);
        return objectTypeService.serialize(objectType);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<CodeNameView> getObjectTypes(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<ObjectType> objectsTypes = objectTypeService.getObjectsTypes(token);
        return objectsTypes.stream().map(objectTypeService::serialize).collect(Collectors.toList());
    }

    @PatchMapping(value = "/{activityTypeCode}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CodeNameView updateObjectType(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                         @PathVariable String activityTypeCode,
                                         @RequestBody CodeNamePatch codeNamePatch) {
        ObjectType objectType = objectTypeService.getPatchedObjectType(token, activityTypeCode, codeNamePatch);
        return objectTypeService.serialize(objectType);
    }

}
