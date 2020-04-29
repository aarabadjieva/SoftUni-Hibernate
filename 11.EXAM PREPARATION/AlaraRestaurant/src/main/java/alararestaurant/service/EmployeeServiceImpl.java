package alararestaurant.service;

import alararestaurant.domain.dtos.importDto.EmployeeImportDto;
import alararestaurant.domain.dtos.importDto.PositionImportDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Position;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.PositionRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final static String EMPLOYEE_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\AlaraRestaurant\\src\\main\\resources\\files\\employees.json";

    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final FileUtil fileUtil;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PositionRepository positionRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validator, FileUtil fileUtil) {
        this.employeeRepository = employeeRepository;
        this.positionRepository = positionRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.fileUtil = fileUtil;
    }

    @Override
    public Boolean employeesAreImported() {
        return this.employeeRepository.count() > 0;
    }

    @Override
    public String readEmployeesJsonFile() throws IOException {
        return this.fileUtil.readFile(EMPLOYEE_JSON_FILE_PATH);
    }

    @Override
    public String importEmployees(String employees) throws IOException {
        employees = readEmployeesJsonFile();
        EmployeeImportDto[] employeeDtos = gson.fromJson(employees, EmployeeImportDto[].class);
        StringBuilder sb = new StringBuilder();
        for (EmployeeImportDto employeeDto : employeeDtos) {
            Position position = this.positionRepository.findByName(employeeDto.getPosition()).orElse(null);
            if (position == null) {
                PositionImportDto positionDto = new PositionImportDto(employeeDto.getPosition());
                position = modelMapper.map(positionDto, Position.class);
                if (!validator.isValid(position)) {
                    sb.append(validator.violations(position)).append(System.lineSeparator());
                    continue;
                }
            }
            Employee employee = modelMapper.map(employeeDto, Employee.class);
            employee.setPosition(position);
            if (!validator.isValid(employee)) {
                sb.append(validator.violations(employee)).append(System.lineSeparator());
                continue;
            }
            positionRepository.saveAndFlush(position);
            employeeRepository.saveAndFlush(employee);
            sb.append(String.format("Record %s successfully imported", employee.getName()))
                    .append(System.lineSeparator());

        }

        return sb.toString();
    }
}
