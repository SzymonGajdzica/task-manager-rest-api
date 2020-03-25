package pl.polsl.task.manager.rest.api.views;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@NoArgsConstructor
@ToString
public class ActivityTypePatch {

    @Nullable
    private String name;

}
