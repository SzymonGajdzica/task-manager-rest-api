package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public class ExceptionView {

    @ApiModelProperty(required = true, example = "NotAuthorizedException")
    @NonNull
    private String exception;

    @ApiModelProperty(required = true, example = "Not allowed to execute this request", position = 1)
    @NonNull
    private String message;

    @ApiModelProperty(required = true, position = 2)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date timestamp = new Date();

}
