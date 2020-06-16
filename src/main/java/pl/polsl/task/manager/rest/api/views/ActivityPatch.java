package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
@ToString
public class ActivityPatch extends ActionPatch {

    @ApiModelProperty(example = "REF", position = 1)
    @Nullable
    private String activityTypeCode;

    @ApiModelProperty(hidden = true)
    private Boolean hasActivityTypeCode = false;

    @ApiModelProperty(position = 2)
    @Nullable
    private Long workerId;

    @ApiModelProperty(hidden = true)
    private Boolean hasWorkerId = false;

    public void setActivityTypeCode(@Nullable String activityTypeCode) {
        this.activityTypeCode = activityTypeCode;
        this.hasActivityTypeCode = true;
    }

    public void setWorkerId(@Nullable Long workerId) {
        this.workerId = workerId;
        this.hasWorkerId = true;
    }
}
