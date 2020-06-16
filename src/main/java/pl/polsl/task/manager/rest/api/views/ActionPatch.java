package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor
@ToString
public abstract class ActionPatch {

    @ApiModelProperty(example = "Fix integer types")
    @Nullable
    private String description;

    @ApiModelProperty(hidden = true)
    private Boolean hasDescription = false;

    public void setDescription(@Nullable String description) {
        this.description = description;
        this.hasDescription = true;
    }

}
