package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "activities")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Activity extends Action {

    @ManyToOne
    @Nullable
    private Worker worker;

    @ManyToOne(optional = false)
    @NonNull
    private ActivityType activityType;

    @ManyToOne(optional = false)
    @NonNull
    private Request request;

}