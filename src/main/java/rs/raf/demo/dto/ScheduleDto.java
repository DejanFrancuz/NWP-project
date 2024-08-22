package rs.raf.demo.dto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rs.raf.demo.enums.MachineOperation;
import rs.raf.demo.model.Machine;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Data
public class ScheduleDto {

    private LocalDateTime executionDateTime;
    private MachineOperation machineOperation;
    private Machine machine;
}
