package psslab.pss.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="roles")
@Data
public class Role {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long roleId;
    private String roleName;

    @ManyToMany()
    private List<User> users;
}
