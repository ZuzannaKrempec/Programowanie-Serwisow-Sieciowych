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

@Entity
@Table(name = "delegations")
@Data
public class Delegation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long delegationId;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTimeStart;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime dateTimeStop;
    private int travelDietAmount;
    private int breakfastNumber;
    private int dinnerNumber;
    private int supperNumber;
    private TransportType transportType;
    private int ticketPrice;
    private AutoCapacity autoCapacity;
    private int km;
    private int accommodationPrice;
    private int otherTicketsPrice;
    private int otherOutlayDesc;
    private int otherOutlayPrice;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ApiModelProperty(hidden = true)
    private User user;

    public boolean valid() {
        return this.dateTimeStart != null &&
                this.dateTimeStop != null &&
                !((this.transportType == TransportType.POCIAG || this.transportType == TransportType.AUTOBUS) && this.ticketPrice == 0) &&
                !(this.transportType == TransportType.AUTO && this.km == 0);
    }

}
