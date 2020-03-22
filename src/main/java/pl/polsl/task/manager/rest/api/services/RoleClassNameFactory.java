package pl.polsl.task.manager.rest.api.services;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.polsl.task.manager.rest.api.exceptions.NotImplementedException;
import pl.polsl.task.manager.rest.api.models.*;

@Component
public class RoleClassNameFactory {

    public String getClassName(@Nullable String roleCode){
        if(roleCode == null)
            return User.class.getSimpleName();
        switch (roleCode) {
            case "MAN": return Manager.class.getSimpleName();
            case "CLI": return Client.class.getSimpleName();
            case "ADM": return Admin.class.getSimpleName();
            case "WOR": return Worker.class.getSimpleName();
            default: throw new NotImplementedException("Missing implementation for role with code " + roleCode);
        }
    }

}
