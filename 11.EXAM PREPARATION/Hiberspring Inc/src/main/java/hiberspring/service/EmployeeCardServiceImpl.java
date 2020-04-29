package hiberspring.service;

import com.google.gson.Gson;
import hiberspring.domain.dtos.importDtos.json.EmployeeCardDto;
import hiberspring.domain.entities.EmployeeCard;
import hiberspring.repository.EmployeeCardRepository;
import hiberspring.util.FileUtil;
import hiberspring.util.ValidationUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeCardServiceImpl implements EmployeeCardService {

    private final static String EMPLOYEE_CRDS_JSON_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\Hiberspring Inc\\src\\main\\resources\\files\\employee-cards.json";

    private final EmployeeCardRepository employeeCardRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper modelMapper;
    private final Gson gson;

    @Autowired
    public EmployeeCardServiceImpl(EmployeeCardRepository employeeCardRepository, FileUtil fileUtil, ValidationUtil validator, ModelMapper modelMapper, Gson gson) {
        this.employeeCardRepository = employeeCardRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public Boolean employeeCardsAreImported() {
        return employeeCardRepository.count() > 0;
    }

    @Override
    public String readEmployeeCardsJsonFile() throws IOException {
        return fileUtil.readFile(EMPLOYEE_CRDS_JSON_FILE_PATH);
    }

    @Override
    public String importEmployeeCards(String employeeCardsFileContent) throws IOException {
        employeeCardsFileContent = readEmployeeCardsJsonFile();
        EmployeeCardDto[] employeeCardDtos = gson.fromJson(employeeCardsFileContent, EmployeeCardDto[].class);
        List<String> result = new ArrayList<>();
        for (EmployeeCardDto employeeCardDto : employeeCardDtos) {
            if (!validator.isValid(employeeCardDto)){
                result.add("Error: Invalid data.");
                continue;
            }
            EmployeeCard employeeCard = modelMapper.map(employeeCardDto, EmployeeCard.class);
            try {
                employeeCardRepository.saveAndFlush(employeeCard);
                result.add(String.format("Successfully imported Employee Card %s.", employeeCard.getNumber()));
            }catch (Exception e){
                result.add("Error: Invalid data.");
            }
        }
        return String.join("\n", result);
    }
}
