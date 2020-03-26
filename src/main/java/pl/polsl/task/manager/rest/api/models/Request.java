package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "requests")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Request extends Action {

    @ManyToOne
    @Nullable
    private Manager manager;

    @OneToMany(mappedBy = "request", cascade = CascadeType.REMOVE)
    @NonNull
    private List<Activity> activities = new LinkedList<>();

    @ManyToOne(optional = false)
    @NonNull
    private Object object;

}