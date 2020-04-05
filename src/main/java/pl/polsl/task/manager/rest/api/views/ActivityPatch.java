package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class ActivityPatch extends ActionPatch {

    @ApiModelProperty(example = "REF", position = 1)
    @Nullable
    private String activityTypeCode;

    @ApiModelProperty(position = 2)
    @Nullable
    private Long workerId;

}
