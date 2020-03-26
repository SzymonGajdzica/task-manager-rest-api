package pl.polsl.task.manager.rest.api.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@NoArgsConstructor
@ToString
public abstract class ActionView {

    @ApiModelProperty(required = true)
    @NonNull
    private Long id;

    @ApiModelProperty(required = true, example = "There is problem with...", position = 1)
    @Nullable
    private String description;

    @ApiModelProperty(required = true, example = "OPN", position = 2)
    @NonNull
    private String statusCode;

    @ApiModelProperty(required = true, example = "Done!", position = 3)
    @Nullable
    private String result;

    @ApiModelProperty(required = true, position = 4)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NonNull
    private Date registerDate;

    @ApiModelProperty(required = true, position = 5)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Nullable
    private Date endDate;


}