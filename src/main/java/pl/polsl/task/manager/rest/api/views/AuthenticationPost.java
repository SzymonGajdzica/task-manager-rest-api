package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class AuthenticationPost {

    @ApiModelProperty(required = true, example = "admin")
    @NonNull
    private String username;

    @ApiModelProperty(required = true, example = "admin", position = 1)
    @NonNull
    private String password;

}