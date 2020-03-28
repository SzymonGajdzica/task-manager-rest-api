package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class UserPost {

    @ApiModelProperty(required = true, example = "John")
    @NonNull
    private String name;

    @ApiModelProperty(required = true, example = "Bosh", position = 1)
    @NonNull
    private String surname;

    @ApiModelProperty(required = true, example = "JohnBosh33@gmail.com", position = 2)
    @NonNull
    private String email;

    @ApiModelProperty(required = true, example = "admin", position = 3)
    @NonNull
    private String username;

    @ApiModelProperty(required = true, example = "admin", position = 4)
    @NonNull
    private String password;

}