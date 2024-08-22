package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;
import rs.raf.demo.enums.MachineStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Enumerated(EnumType.STRING)
    private MachineStatus status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "createdBy")
    private User createdBy;

    @Column
    private Date dateCreated;


    @Column
    private Boolean active;

    @Column
    private Long ciclusCount;

    @Column
    private Boolean deleted;

    public Machine() {
    }

    public Machine(MachineStatus status, User createdBy, Boolean active, String name) {
        this.status = status;
        this.createdBy = createdBy;
        this.active = active;
        this.name = name;
        this.deleted = false;
        this.dateCreated = new Date();

    }

}