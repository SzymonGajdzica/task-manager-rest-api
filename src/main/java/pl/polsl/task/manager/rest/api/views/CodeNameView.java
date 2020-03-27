package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class CodeNameView {

    @ApiModelProperty(example = "PRG")
    @NonNull
    private String code;

    @ApiModelProperty(example = "Programming", position = 1)
    @NonNull
    private String name;

}

