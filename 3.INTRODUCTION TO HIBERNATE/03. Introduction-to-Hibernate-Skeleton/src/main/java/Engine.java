import entities.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Engine implements Runnable {

    private final EntityManager entityManager;

    public Engine(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void run() {
        this.employeesMaximumSalary();
    }

    private void employeesMaximumSalary() {
        this.entityManager.getTransaction().begin();
        List<Object[]> result = this.entityManager
                .createNativeQuery("SELECT d.name, MAX(e.salary) FROM employees as e JOIN departments AS d " +
                        "ON d.department_id = e.department_id\n" +
                        "GROUP BY d.department_id\n" +
                        "HAVING MAX(e.salary) NOT BETWEEN 30000 and 70000")
                .getResultList();
        for (Object[] r:result
             ) {
            System.out.printf("%s - %.2f%n",r[0], r[1]);
        }
    }

    private void findEmployeesByFirstName() {
        Scanner scanner = new Scanner(System.in);
        String pattern = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        try {
            List<Employee> employees = this.entityManager
                    .createQuery("FROM Employee as e WHERE e.firstName LIKE :pattern", Employee.class)
                    .setParameter("pattern", pattern + "%")
                    .getResultList();

            for (Employee e:employees
                 ) {
                System.out.printf("%s %s - %s - ($%.2f)%n",e.getFirstName(), e.getLastName(),
                        e.getJobTitle(), e.getSalary());
            }
        }catch (Exception e){
            this.entityManager.getTransaction().rollback();
        }
    }

    private void containsEmployee() {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        try {
            Employee employee = this.entityManager
                    .createQuery("FROM Employee WHERE CONCAT(firstName, ' ',lastName) = :name", Employee.class)
                    .setParameter("name", name)
                    .getSingleResult();
            System.out.println("Yes");
        } catch (Exception e) {
            System.out.println("No");
        }
        this.entityManager.getTransaction().commit();
    }

    private void removeObject() {
        List<Town> towns = this.entityManager
                .createQuery("FROM Town", Town.class)
                .getResultList();
        for (Town town : towns
        ) {
            if (town.getName().length() > 5) {
                this.entityManager.detach(town);
            }
        }
        this.entityManager.getTransaction().begin();
        for (Town town : towns
        ) {
            town.setName(town.getName().toLowerCase());
        }
        this.entityManager.getTransaction().commit();
    }

    private void employeesWithSalaryOver50000() {
        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee as e WHERE e.salary>50000", Employee.class)
                .getResultList();
        for (Employee employee : employees
        ) {
            System.out.println(employee.getFirstName());
        }
        this.entityManager.getTransaction().begin();
    }

    private void employeesFromDepartment() {
        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee as e WHERE e.department.name = 'Research and Development' ORDER BY e.salary, e.id", Employee.class)
                .getResultList();
        for (Employee e : employees
        ) {
            System.out.printf("%s %s from %s - $%.2f%n"
                    , e.getFirstName(), e.getLastName(), e.getDepartment().getName(), e.getSalary());
        }
        this.entityManager.getTransaction().begin();
    }

    private void addNewAddressAndUpdateEmployee() {
        this.entityManager.getTransaction().begin();
        Town town = this.entityManager.createQuery("FROM Town WHERE id = 32", Town.class)
                .getSingleResult();
        Address address = new Address();
        address.setText("Vitoshka 15");
        address.setTown(town);

        this.entityManager.persist(address);
        this.entityManager.getTransaction().commit();


        Scanner scanner = new Scanner(System.in);
        String lastName = scanner.nextLine();
        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee as e WHERE e.lastName = :lastname", Employee.class)
                .setParameter("lastname", lastName)
                .getResultList();
        for (Employee e : employees
        ) {
            e.setAddress(address);
        }
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void addressesWithEmployeeCount(){
        this.entityManager.getTransaction().begin();
        List<Address> addresses = this.entityManager
                .createQuery("FROM Address as a ORDER BY size(a.employees) DESC, a.town.name", Address.class)
                .setMaxResults(10)
                .getResultList();
        for (Address a:addresses
             ) {
            System.out.printf("%s, %s - %d employees%n", a.getText(), a.getTown().getName(), a.getEmployees().size());
        }
    }

    private void employeeWithProjects(){
        Scanner scanner = new Scanner(System.in);
        int id = Integer.parseInt(scanner.nextLine());
        this.entityManager.getTransaction().begin();
        Employee employee = this.entityManager
                .createQuery("FROM Employee as e WHERE e.id = :id", Employee.class)
                .setParameter("id",id)
                .getSingleResult();

        System.out.printf("%s %s - %s%n", employee.getFirstName(), employee.getLastName(),employee.getJobTitle());
        Set<Project> projects = employee.getProjects();
        projects.stream().sorted(Comparator.comparing(Project::getName)).forEach(p-> System.out.printf("      %s%n",p.getName()));
    }

    private void latest10projects(){
        this.entityManager.getTransaction().begin();
        List<Project> projects = this.entityManager
                .createQuery("FROM Project as p ORDER BY p.startDate DESC", Project.class)
                .setMaxResults(10)
                .getResultList();

        projects.stream().sorted(Comparator.comparing(Project::getName))
                .forEach(p->System.out.printf("Project name: %s\n" +
                                " \tProject Description: %s\n" +
                                " \tProject Start Date:%s\n" +
                                " \tProject End Date: %s\n", p.getName(), p.getDescription(),
                        p.getStartDate(), p.getEndDate()));

    }

    private void increaseSalaries(){
        this.entityManager.getTransaction().begin();
        List<Employee> employees = this.entityManager
                .createQuery("FROM Employee as e WHERE e.department.id IN (1,2,4,11)",Employee.class)
                .getResultList();
        for (Employee e:employees
             ) {
            e.setSalary(e.getSalary().multiply(BigDecimal.valueOf(1.12)));
            System.out.printf("%s %s ($%s)%n",e.getFirstName(),e.getLastName(),e.getSalary());
        }
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    private void removeTowns(){
        this.entityManager.getTransaction().begin();
        Scanner scanner = new Scanner(System.in);
        String townName = scanner.nextLine();
        Town town = this.entityManager
                .createQuery("FROM Town as t WHERE t.name = :name",Town.class)
                .setParameter("name",townName)
                .getSingleResult();
        try {
            List<Address> addresses = this.entityManager
                    .createQuery("FROM Address as a WHERE a.town.id = :townId", Address.class)
                    .setParameter("townId", town.getId())
                    .getResultList();
            System.out.printf("%d address in %s deleted", addresses.size(), townName);
            for (Address a : addresses
            ) {
                a.getEmployees().stream().forEach(e->e.setAddress(null));
                this.entityManager.remove(a);
            }
            this.entityManager.remove(town);
        }catch (Exception e){
            this.entityManager.getTransaction().rollback();
        }
        this.entityManager.getTransaction().commit();
    }


}
