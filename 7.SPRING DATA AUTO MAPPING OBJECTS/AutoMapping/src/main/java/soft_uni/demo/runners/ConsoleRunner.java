package soft_uni.demo.runners;

import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import soft_uni.demo.entities.Address;
import soft_uni.demo.entities.Employee;
import soft_uni.demo.entities.dtos.EmployeeDto;
import soft_uni.demo.entities.dtos.ManagerDto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ConsoleRunner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        Address address = new Address("Sofia");
        Employee employee = new Employee("Pesho", "Peshov", BigDecimal.valueOf(1700L), LocalDate.parse("1990-11-17"), address, false);
        Employee employee2 = new Employee("Maria", "Petrova", BigDecimal.valueOf(822L), LocalDate.parse("1990-06-07"), address, false);
        Employee employee3 = new Employee("Ivan", "Georgiev", BigDecimal.valueOf(5000L), LocalDate.parse("1988-07-08"), address, true);
        Employee employee4 = new Employee("Vasil", "Slavov", BigDecimal.valueOf(1200L), LocalDate.parse("1992-11-11"), address, false);
        Employee employee5 = new Employee("Georgi", "Dimitrov", BigDecimal.valueOf(11000L), LocalDate.parse("1962-03-12"), address, false);
        Employee employee6 = new Employee("Dimitur", "Toshev", BigDecimal.valueOf(1200L), LocalDate.parse("1984-01-11"), address, true);
        Employee employee7 = new Employee("Aneliq", "Karova", BigDecimal.valueOf(1700L), LocalDate.parse("1979-02-02"), address, false);
        employee.setManager(employee2);
        employee.getEmployees().add(employee3);
        employee.getEmployees().add(employee4);
        employee.getEmployees().add(employee5);
        EmployeeDto employeeDto = employeeToEmployeeDto(modelMapper, employee);
        EmployeeDto employeeDto2 = employeeToEmployeeDto(modelMapper, employee2);
        EmployeeDto employeeDto3 = employeeToEmployeeDto(modelMapper, employee3);
        EmployeeDto employeeDto4 = employeeToEmployeeDto(modelMapper, employee4);
        EmployeeDto employeeDto5 = employeeToEmployeeDto(modelMapper, employee5);
        EmployeeDto employeeDto6 = employeeToEmployeeDto(modelMapper, employee6);
        EmployeeDto employeeDto7 = employeeToEmployeeDto(modelMapper, employee7);





        ManagerDto managerDto = new ManagerDto();
                modelMapper.map(employee, managerDto);
        System.out.printf("%s %s | Employees: %d\n%s", managerDto.getFirstName(), managerDto.getLastName(),
                managerDto.getEmployees().size(), managerDto.employeesToString());
    }

    private EmployeeDto employeeToEmployeeDto(ModelMapper modelMapper, Employee employee) {
        return modelMapper.map(employee, EmployeeDto.class);
    }
}
