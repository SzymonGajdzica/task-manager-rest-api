package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class UserView {

    @ApiModelProperty(required = true)
    @NonNull
    private Long id;

    @ApiModelProperty(required = true, example = "John", position = 1)
    @NonNull
    private String name;

    @ApiModelProperty(required = true, example = "Bosh", position = 2)
    @NonNull
    private String surname;

    @ApiModelProperty(required = true, example = "John33@gmail.com", position = 3)
    @NonNull
    private String email;

    @ApiModelProperty(required = true, example = "John33", position = 4)
    @NonNull
    private String username;

    @ApiModelProperty(example = "ADM", position = 5)
    @Nullable
    private String roleCode;

}