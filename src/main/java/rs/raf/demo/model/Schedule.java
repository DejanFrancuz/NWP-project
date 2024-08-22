package rs.raf.demo.model;

import lombok.Getter;
import lombok.Setter;
import rs.raf.demo.enums.MachineOperation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MachineOperation operationType;

    private LocalDateTime executionDateTime;

    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;
}
