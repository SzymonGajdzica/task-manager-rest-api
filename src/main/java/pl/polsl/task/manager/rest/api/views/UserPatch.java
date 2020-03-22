package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class UserPatch {

    @ApiModelProperty(example = "John")
    @Nullable
    private String name;

    @ApiModelProperty(example = "Bosh")
    @Nullable
    private String surname;

    @ApiModelProperty(example = "John33@gmail.com")
    @Nullable
    private String email;

}
