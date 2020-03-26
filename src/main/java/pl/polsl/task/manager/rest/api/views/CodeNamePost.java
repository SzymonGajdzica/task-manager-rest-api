package pl.polsl.task.manager.rest.api.views;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
@ToString
public class CodeNamePost {

    @NonNull
    private String code;

    @NonNull
    private String name;

}
