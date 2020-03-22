package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
@ToString
public class ExceptionView {

    @ApiModelProperty(required = true, example = "NotAuthorizedException")
    @Setter(AccessLevel.NONE)
    @NonNull
    private String title;

    @ApiModelProperty(required = true, example = "Not allowed to execute this request", position = 1)
    @Setter(AccessLevel.NONE)
    @NonNull
    private String description;

}
