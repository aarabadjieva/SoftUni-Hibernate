package hiberspring.service;

import hiberspring.domain.dtos.importDtos.xml.EmployeeDto;
import hiberspring.domain.dtos.importDtos.xml.EmployeeRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Employee;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.repository.EmployeeRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import hiberspring.util.XmlParser;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEES_XML_FILA_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\Hiberspring Inc\\src\\main\\resources\\files\\employees.xml";

    private final EmployeeRepository employeeRepository;
    private final BranchRepository branchRepository;
    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, BranchRepository branchRepository, EmployeeCardRepository employeeCardRepository, FileUtil fileUtil, ValidationUtil validator, ModelMapper modelMapper, XmlParser xmlParser) {
        this.employeeRepository = employeeRepository;
        this.branchRepository = branchRepository;
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean employeesAreImported() {
        return employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesXmlFile() throws IOException {
        return fileUtil.readFile(EMPLOYEES_XML_FILA_PATH);
    }

    @Override
    public String importEmployees() throws JAXBException {
        EmployeeRootDto employeeRootDto = xmlParser.parseXml(EmployeeRootDto.class, EMPLOYEES_XML_FILA_PATH);
        List<String> result = new ArrayList<>();
        for (EmployeeDto employeeDto : employeeRootDto.getEmployeeDtos()) {
            EmployeeCard employeeCard = employeeCardRepository.findByNumber(employeeDto.getCard()).orElse(null);
            Branch branch = branchRepository.findByName(employeeDto.getBranch()).orElse(null);

            if (employeeCard==null||branch==null||!validator.isValid(employeeDto)){
                result.add("Error: Invalid data.");
                continue;
            }

            Employee employee = modelMapper.map(employeeDto, Employee.class);
            employee.setBranch(branch);
            employee.setCard(employeeCard);

            try {
                employeeRepository.saveAndFlush(employee);
                result.add(String.format("Successfully imported Employee %s %s.", employee.getFirstName(), employee.getLastName()));
            }catch (Exception e){
                result.add("Error: Invalid data.");
            }
        }
        return String.join("\n", result);
    }

    @Override
    public String exportProductiveEmployees() {
        List<Employee> productiveEmployees = employeeRepository.findAllByBranchProducts();
        StringBuilder sb = new StringBuilder();
        for (Employee productiveEmployee : productiveEmployees) {
            sb.append(String.format("Name: %s %s\n" +
                    "Position: %s\n" +
                    "Card Number: %s\n", productiveEmployee.getFirstName(),
                    productiveEmployee.getLastName(),productiveEmployee.getPosition(),
                    productiveEmployee.getCard().getNumber())).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
