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
public class ActivityView extends ActionView {

    @ApiModelProperty(required = true, position = 6)
    @NonNull
    private Long requestId;

    @ApiModelProperty(example = "REF", position = 7)
    @Nullable
    private String activityTypeCode;

    @ApiModelProperty(position = 8)
    @Nullable
    private Long workerId;

}
