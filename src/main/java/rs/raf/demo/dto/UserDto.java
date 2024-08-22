package rs.raf.demo.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Data
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String[] permissions;
}
