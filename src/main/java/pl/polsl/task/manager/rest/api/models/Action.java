package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class Action extends BaseModelWithId {

    @Column(name = "description")
    @Nullable
    private String description;

    @ManyToOne
    @NonNull
    private Status status;

    @Column(name = "result")
    @Nullable
    private String result;

    @Column(name = "register_date", nullable = false)
    @NonNull
    private Date registerDate = new Date();

    @Column(name = "end_date")
    @Nullable
    private Date endDate = null;

}