package pl.polsl.task.manager.rest.api.services;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;
import pl.polsl.task.manager.rest.api.exceptions.BaseException;

import java.util.Map;

@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
        errorAttributes.put("exception", getError(webRequest).getClass().getSimpleName());
        errorAttributes.put("custom", getError(webRequest) instanceof BaseException);
        return errorAttributes;
    }
}