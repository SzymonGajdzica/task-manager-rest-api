package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class ActivityPatch {

    @ApiModelProperty(example = "Fix integer types")
    @Nullable
    private String description;

    @ApiModelProperty(example = "REF", position = 1)
    @Nullable
    private String activityTypeCode;

    @ApiModelProperty(position = 2)
    @Nullable
    private Long workerId;

}
