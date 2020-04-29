package soft_uni.demo.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
}
