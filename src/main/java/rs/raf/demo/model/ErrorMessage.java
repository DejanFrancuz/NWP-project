package rs.raf.demo.model;

import rs.raf.demo.enums.MachineOperation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ErrorMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime errorTime;

    private Long machineId;

    @Enumerated(EnumType.STRING)
    private MachineOperation operationType;

    private String errorMessage;

}
