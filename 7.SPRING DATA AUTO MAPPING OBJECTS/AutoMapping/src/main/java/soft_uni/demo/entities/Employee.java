package soft_uni.demo.entities;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Employee {
    private String firstName;
    private String lastName;
    private BigDecimal salary;
    private LocalDate birthday;
    private Address address;
    private boolean isOnHoliday;
    private Employee manager;
    private List<Employee> employees;

    public Employee(String firstName, String lastName, BigDecimal salary, LocalDate birthday, Address address, boolean isOnHoliday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.salary = salary;
        this.birthday = birthday;
        this.address = address;
        this.isOnHoliday = isOnHoliday;
        this.manager = null;
        this.employees = new ArrayList<>();
    }
}
