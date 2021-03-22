package psslab.pss.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    private String companyName;
    private String companyAddress;
    private String companyNip;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private boolean status;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime registrationDate;

    @ManyToMany()
    private List<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @ApiModelProperty(hidden = true)
    private List<Delegation> delegations;

    public boolean valid() {
        return this.companyName != null &&
                this.companyAddress != null &&
                this.companyNip != null &&
                this.name != null &&
                this.lastName != null &&
                this.email != null &&
                this.password != null;

    }

    public void setTestData() {
        this.userId = 1L;
        this.companyName = "companyName";
        this.companyAddress = "companyAddress";
        this.companyNip = "companyNip";
        this.name = "name";
        this.lastName = "lastName";
        this.email = "email";
        this.password = "password";
        this.status = true;
        this.registrationDate = LocalDateTime.now();
        this.roles = new ArrayList<>();
        this.delegations = new ArrayList<>();
    }
}
