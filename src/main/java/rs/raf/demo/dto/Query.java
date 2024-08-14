package rs.raf.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Data
@AllArgsConstructor
public class Query {

    private String name;
    private String[] status;
    private String dateFrom;
    private String dateTo;
}
