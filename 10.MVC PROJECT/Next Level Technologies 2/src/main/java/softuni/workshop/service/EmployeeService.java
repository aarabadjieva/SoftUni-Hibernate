package softuni.workshop.service;

import javax.xml.bind.JAXBException;
import java.io.IOException;

public interface EmployeeService {

    void importEmployees() throws JAXBException, IOException;

    boolean areImported();

    String readEmployeesXmlFile() throws IOException;

    String exportEmployeesWithAgeAbove();

    void exportEmployees() throws JAXBException, IOException;

    void exportEmployeesToJson() throws IOException;

    String readEmployeesJsonFile() throws IOException;

    boolean areExported() throws IOException;

    String exportEmployeesWithGivenName();

    String exportEmployeesWithGivenProjectName();
}
