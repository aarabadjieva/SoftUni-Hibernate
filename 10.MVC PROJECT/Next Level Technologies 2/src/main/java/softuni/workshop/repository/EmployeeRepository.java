package softuni.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import softuni.workshop.domain.entities.Employee;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    List<Employee> findAllByAgeGreaterThanOrderByAge(Integer age);

    List<Employee> findAllByLastNameOrderByFirstName(String lastName);

    @Query("select e from Employee as e " +
            "where e.project.name = :projectName " +
            "order by e.id")
    List<Employee> findAllEmployeesWithProjectName(@Param(value = "projectName") String projectName);
}
