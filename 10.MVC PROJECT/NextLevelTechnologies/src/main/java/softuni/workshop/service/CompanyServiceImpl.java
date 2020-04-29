package softuni.workshop.service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.workshop.domain.dtos.CompaniesRoot;
import softuni.workshop.domain.dtos.CompanyDto;
import softuni.workshop.domain.entities.Company;
import softuni.workshop.repository.CompanyRepository;
import softuni.workshop.util.FileUtil;
import softuni.workshop.util.ValidatorUtil;
import softuni.workshop.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final static String COMPANIES_IMPORT_XML = "D:\\Programming\\6.Hibernate\\10.MVC PROJECT\\NextLevelTechnologies\\src\\main\\resources\\files\\xmls\\companies.xml";

    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validator;
    private final XmlParser xmlParser;
    private final FileUtil fileUtil;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, ValidatorUtil validator, XmlParser xmlParser, FileUtil fileUtil) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.xmlParser = xmlParser;
        this.fileUtil = fileUtil;
    }

    @Override
    public void importCompanies() throws JAXBException, IOException {
        CompaniesRoot companiesRoot = (CompaniesRoot) this.xmlParser.importParse(CompaniesRoot.class, readCompaniesXmlFile());
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
        return this.companyRepository.count()!=0;
    }

    @Override
    public String readCompaniesXmlFile() throws IOException {
        return this.fileUtil.fileContent(COMPANIES_IMPORT_XML);
    }
}
