package softuni.workshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.EmployeeDto;
import softuni.workshop.domain.dtos.EmployeesRoot;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEES_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\NextLevelTechnologies\\src\\main\\resources\\files\\xmls\\employees.xml";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final FileUtil fileUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validator, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importEmployees() throws IOException, JAXBException {
        EmployeesRoot employeesRoot = (EmployeesRoot) this.xmlParser.importParse(EmployeesRoot.class, readEmployeesXmlFile());
        for (EmployeeDto employeeDto : employeesRoot.getEmployeeDtoList()) {
            Project project = this.projectRepository.findByName(employeeDto.getProject().getName());
            Employee employee = this.modelMapper.map(employeeDto, Employee.class);
            employee.setProject(project);
            if (!this.validator.isValid(employee)){
                System.out.println(this.validator.violations(employee));
            }else {
                this.employeeRepository.saveAndFlush(employee);
            }
        }
    }


    @Override
    public boolean areImported() {
       return this.employeeRepository.count()!=0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.fileContent(EMPLOYEES_IMPORT_XML);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        List<Employee> employeesOver25YearsOld = this.employeeRepository.findAllByAgeGreaterThan(25);
        StringBuilder sb = new StringBuilder();
        for (Employee e : employeesOver25YearsOld) {
            sb.append(String.format("Full Name: %s %s\n\tAge: %d\n\tProject Name: %s",
                    e.getFirstName(), e.getLastName(), e.getAge(), e.getProject().getName()))
                    .append(System.lineSeparator());
        }
        return sb.toString().trim();
    }
}
