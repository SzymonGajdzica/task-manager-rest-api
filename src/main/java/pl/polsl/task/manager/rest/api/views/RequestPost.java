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
public class RequestPost {

    @ApiModelProperty(example = "Problem with input types")
    @Nullable
    private String description;

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long objectId;

}