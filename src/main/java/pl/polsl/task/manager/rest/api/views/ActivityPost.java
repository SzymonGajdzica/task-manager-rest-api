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
public class ActivityPost {

    @ApiModelProperty(example = "Fix integer types")
    @Nullable
    private String description;

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long requestId;

    @ApiModelProperty(required = true, example = "REF", position = 2)
    @NonNull
    private String activityCode;

    @ApiModelProperty(position = 3)
    @Nullable
    private Long workerId;

}
