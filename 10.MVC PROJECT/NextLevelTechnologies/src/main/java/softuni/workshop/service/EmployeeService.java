package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EmployeeService {

    void importEmployees() throws IOException, JAXBException;

    boolean areImported();

    String readEmployeesXmlFile() throws IOException;

    String exportEmployeesWithAgeAbove();
}
