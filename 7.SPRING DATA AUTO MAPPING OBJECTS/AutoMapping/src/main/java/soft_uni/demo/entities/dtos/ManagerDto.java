package soft_uni.demo.entities.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ManagerDto {
    private String firstName;
    private String lastName;
    private List<EmployeeDto> employees;

    public String employeesToString(){
       final StringBuilder employeeString = new StringBuilder();
       this.employees.forEach(e-> employeeString.append(String.format("   - %s %s %.2f\n",e.getFirstName(), e.getLastName(), e.getSalary())));
       return employeeString.toString();
    }
}
