package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

import java.util.List;

@Data
@NoArgsConstructor
@ToString
public class ActionStatusView {

    @ApiModelProperty(required = true, example = "OPN")
    @NonNull
    private String code;

    @ApiModelProperty(required = true, example = "Open", position = 1)
    @NonNull
    private String name;

    @ApiModelProperty(required = true, position = 2)
    @NonNull
    private List<String> childrenCode;

}