package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public abstract class ActionPatch {

    @ApiModelProperty(example = "Fix integer types")
    @Nullable
    private String description;

}
