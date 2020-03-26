package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class ActivityPost extends ActionPost {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long requestId;

    @ApiModelProperty(example = "REF", position = 2)
    @Nullable
    private String activityTypeCode;

    @ApiModelProperty(position = 3)
    @Nullable
    private Long workerId;

}
