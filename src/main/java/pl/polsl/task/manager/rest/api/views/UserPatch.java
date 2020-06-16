package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class UserPatch {

    @ApiModelProperty(example = "John")
    @Nullable
    private String name;

    @ApiModelProperty(example = "Bosh", position = 1)
    @Nullable
    private String surname;

    @ApiModelProperty(example = "John33@gmail.com", position = 2)
    @Nullable
    private String email;

    @Setter(AccessLevel.NONE)
    @ApiModelProperty(example = "MAN", position = 3)
    @Nullable
    private String roleCode;

    @ApiModelProperty(hidden = true)
    private Boolean hasRoleCode = false;

    @ApiModelProperty(example = "admin1", position = 4)
    @Nullable
    private String password;

    public void setRoleCode(@Nullable String roleCode) {
        this.roleCode = roleCode;
        this.hasRoleCode = true;
    }


}
