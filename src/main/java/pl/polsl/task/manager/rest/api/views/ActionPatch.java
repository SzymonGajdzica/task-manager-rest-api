package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class ActionPatch {

    @ApiModelProperty(example = "OPN")
    @Nullable
    private String statusCode;

    @ApiModelProperty(example = "Done!", position = 1)
    @Nullable
    private String result;

}
