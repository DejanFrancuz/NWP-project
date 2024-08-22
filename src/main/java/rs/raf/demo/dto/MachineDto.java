package rs.raf.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import rs.raf.demo.enums.MachineStatus;
import rs.raf.demo.model.User;

@Getter
@Setter
@Data
public class MachineDto {

    private String username;
    private MachineStatus status;
    private boolean active;
}
