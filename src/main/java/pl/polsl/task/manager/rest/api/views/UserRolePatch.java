package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class UserRolePatch {

    @ApiModelProperty(example = "MAN")
    @Nullable
    private  String roleCode;

}
