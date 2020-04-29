package softuni.workshop.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.importDto.CompaniesRoot;
import softuni.workshop.domain.dtos.importDto.CompanyDto;
import softuni.workshop.domain.dtos.jsonDtos.exportDto.CompanyExportDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final static String COMPANIES_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\xmls\\companies.xml";
    private final static String COMPANIES_JSON_FILE = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\Next Level Technologies 2\\src\\main\\resources\\files\\jsons\\companies.json";

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;
    private final Gson gson;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, ValidatorUtil validator, XmlParser xmlParser, FileUtil fileUtil, Gson gson) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
        this.gson = gson;
    }

    @Override
    public void importCompanies() throws JAXBException, IOException {
        CompaniesRoot companiesRoot = (CompaniesRoot) this.xmlParser.importXMl(CompaniesRoot.class, readCompaniesXmlFile());
        for (CompanyDto companyDto : companiesRoot.getCompanyDtoList()) {
            Company company = this.modelMapper.map(companyDto, Company.class);
            if (!this.validator.isValid(company)){
                System.out.println(this.validator.violations(company));
            }else {
                this.companyRepository.saveAndFlush(company);
            }
        }
    }

    @Override
    public boolean areImported() {
       return this.companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return this.fileUtil.readFile(COMPANIES_IMPORT_XML);
    }


    @Override
    public void exportJsonCompanies() throws IOException {
        List<CompanyExportDto> companies = this.companyRepository.findAll()
                .stream()
                .map(c->this.modelMapper.map(c, CompanyExportDto.class))
                .collect(Collectors.toList());
        String content = this.gson.toJson(companies);
        FileWriter writer = new FileWriter(new File(COMPANIES_JSON_FILE));
        writer.write(content);
        writer.close();
    }

    @Override
    public String readCompaniesJsonFile() throws IOException {
        return this.fileUtil.readFile(COMPANIES_JSON_FILE);
    }

    @Override
    public boolean areExported() throws IOException {
       return this.readCompaniesJsonFile().length()>0;
    }
}
