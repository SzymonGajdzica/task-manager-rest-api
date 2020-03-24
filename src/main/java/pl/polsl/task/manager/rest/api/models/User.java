package pl.polsl.task.manager.rest.api.models;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.*;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@Data
@NoArgsConstructor
@ToString
public class User extends BaseEntityWithId {

    @Column(name = "name", nullable = false)
    @NonNull
    private String name;

    @Column(name = "surname", nullable = false)
    @NonNull
    private String surname;

    @Column(name = "email", nullable = false)
    @NonNull
    private String email;

    @Column(name = "username", nullable = false, unique = true)
    @NonNull
    private String username;

    @Column(name = "password", nullable = false)
    @NonNull
    private String password;

    @ManyToOne
    @Nullable
    private Role role;

}
