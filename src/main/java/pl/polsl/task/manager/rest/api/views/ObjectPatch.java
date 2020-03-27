package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class ObjectPatch {

    @ApiModelProperty(required = true, example = "IOS", position = 1)
    @Nullable
    private String name;

}