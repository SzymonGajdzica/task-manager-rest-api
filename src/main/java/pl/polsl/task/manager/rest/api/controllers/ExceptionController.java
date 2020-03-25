package pl.polsl.task.manager.rest.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.polsl.task.manager.rest.api.exceptions.*;
import pl.polsl.task.manager.rest.api.views.ExceptionView;

@ControllerAdvice
public class ExceptionController {

    @ResponseBody
    @ExceptionHandler(NotAuthorizedException.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ExceptionView notAuthorizedActionHandler(NotAuthorizedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(ForbiddenAccessException.class)
    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    public ExceptionView forbiddenAccessHandler(ForbiddenAccessException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotImplementedException.class)
    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    public ExceptionView notImplementedHandler(NotImplementedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(UsernameAlreadyUsedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionView usernameAlreadyUsedHandler(UsernameAlreadyUsedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(CodeAlreadyUsedException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionView usernameAlreadyUsedHandler(CodeAlreadyUsedException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(WrongRequestException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ExceptionView wrongRequestHandler(WrongRequestException e) {
        return generateBasicMessage(e);
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ExceptionView entityNotFoundExceptionHandler(NotFoundException e) {
        return generateBasicMessage(e);
    }

    private ExceptionView generateBasicMessage(Exception e){
        ExceptionView exceptionView = new ExceptionView();
        exceptionView.setException(e.getClass().getSimpleName());
        exceptionView.setMessage(e.getMessage());
        return exceptionView;
    }


}
