package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.EmployeeDto;
import softuni.workshop.domain.dtos.importDto.EmployeesRoot;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.EmployeeExportDto;
import softuni.workshop.domain.entities.Employee;
import softuni.workshop.domain.entities.Project;
import softuni.workshop.repository.EmployeeRepository;
import softuni.workshop.repository.ProjectRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.transaction.Transactional;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEES_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\xmls\\employees.xml";
    private final static String EMPLOYEES_JSON_FILE ="D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\jsons\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final ProjectRepository projectRepository;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;
    private final ValidatorUtil validator;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ModelMapper modelMapper, XmlParser xmlParser, ValidatorUtil validator, FileUtil fileUtil, Gson gson) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
        this.validator = validator;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void importEmployees() throws JAXBException, IOException {
        EmployeesRoot employeesRoot = (EmployeesRoot) this.xmlParser.importXMl(EmployeesRoot.class, readEmployeesXmlFile());
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
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_IMPORT_XML);
    }

    @Override
    public String exportEmployeesWithAgeAbove() {
        List<EmployeeExportDto> employeesOverAge = this.employeeRepository.findAllByAgeGreaterThanOrderByAge(25)
                .stream()
                .map(e->this.modelMapper.map(e, EmployeeExportDto.class))
                .collect(Collectors.toList());
        return  this.gson.toJson(employeesOverAge);
    }

    @Override
    public void exportEmployees() throws JAXBException, IOException {

    }


    @Override
    public void exportEmployeesToJson() throws IOException {
        List<EmployeeExportDto> employees = this.employeeRepository.findAll()
                .stream()
                .map(e->this.modelMapper.map(e, EmployeeExportDto.class))
                .collect(Collectors.toList());
        String content = this.gson.toJson(employees);
        FileWriter writer = new FileWriter(new File(EMPLOYEES_JSON_FILE));
        writer.write(content);
        writer.close();
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEES_JSON_FILE);
    }

    @Override
    public boolean areExported() throws IOException {
       return  this.readEmployeesJsonFile().length()>0;
    }

    @Override
    public String exportEmployeesWithGivenName() {
        List<EmployeeExportDto> employeesWithLastName = this.employeeRepository.findAllByLastNameOrderByFirstName("Hristova")
                .stream()
                .map(e->this.modelMapper.map(e, EmployeeExportDto.class))
                .collect(Collectors.toList());
        return  this.gson.toJson(employeesWithLastName);
    }

    @Override
    public String exportEmployeesWithGivenProjectName() {
        List<EmployeeExportDto> employeesWithProjectName = this.employeeRepository.findAllEmployeesWithProjectName("GitBuh_Project")
                .stream()
                .map(e->this.modelMapper.map(e, EmployeeExportDto.class))
                .collect(Collectors.toList());
        System.out.println();
        return  this.gson.toJson(employeesWithProjectName);
    }
}
