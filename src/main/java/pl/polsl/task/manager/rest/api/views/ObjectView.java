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
public class ObjectView {

    @ApiModelProperty(required = true)
    @NonNull
    private Long id;

    @ApiModelProperty(required = true, example = "IOS", position = 1)
    @Nullable
    private String name;

    @ApiModelProperty(required = true, example = "PRG", position = 2)
    @NonNull
    private String objectTypeCode;

    @ApiModelProperty(required = true, position = 3)
    @NonNull
    private Long clientId;

}
