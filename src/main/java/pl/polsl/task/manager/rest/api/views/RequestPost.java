package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@ToString
public class RequestPost extends ActionPost {

    @ApiModelProperty(required = true, position = 1)
    @NonNull
    private Long objectId;

}