package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import pl.polsl.task.manager.rest.api.models.Object;
import pl.polsl.task.manager.rest.api.services.ObjectService;
import pl.polsl.task.manager.rest.api.views.ObjectPatch;
import pl.polsl.task.manager.rest.api.views.ObjectPost;
import pl.polsl.task.manager.rest.api.views.ObjectView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/object")
public class ObjectController {

    private final ObjectService objectService;

    public ObjectController(ObjectService objectService) {
        this.objectService = objectService;
    }

    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectView createObject(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                   @RequestBody ObjectPost objectPost) {
        Object Object = objectService.createObject(token, objectPost);
        return objectService.serialize(Object);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ObjectView> getObjects(@ApiIgnore @RequestHeader(value = "Authorization") String token) {
        List<Object> objects = objectService.getObjects(token);
        return objects.stream().map(objectService::serialize).collect(Collectors.toList());
    }

    @PatchMapping(value = "/{objectId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ObjectView updateObject(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                                   @PathVariable Long objectId,
                                   @RequestBody ObjectPatch objectPatch) {
        Object Object = objectService.getPatchedObject(token, objectId, objectPatch);
        return objectService.serialize(Object);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{objectId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteRequest(@ApiIgnore @RequestHeader(value = "Authorization") String token,
                              @PathVariable Long objectId) {
        objectService.deleteObject(token, objectId);
    }

}
