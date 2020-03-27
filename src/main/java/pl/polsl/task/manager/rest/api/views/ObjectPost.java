package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class ObjectPost {

    @ApiModelProperty(required = true, example = "IOS")
    @NonNull
    private String name;

    @ApiModelProperty(required = true, example = "PRG", position = 1)
    @NonNull
    private String objectTypeCode;

    @ApiModelProperty(required = true, position = 2)
    @NonNull
    private Long clientId;

}
