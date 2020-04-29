package hiberspring.service;

import hiberspring.domain.dtos.importDtos.xml.ProductDto;
import hiberspring.domain.dtos.importDtos.xml.ProductRootDto;
import hiberspring.domain.entities.Branch;
import hiberspring.domain.entities.Product;
import hiberspring.repository.BranchRepository;
import hiberspring.repository.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    private final static String PRODUCTS_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\Hiberspring Inc\\src\\main\\resources\\files\\products.xml";

    private final ProductRepository productRepository;
    private final BranchRepository branchRepository;
    private final FileUtil fileUtil;
    private final ValidationUtil validator;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, BranchRepository branchRepository, FileUtil fileUtil, ValidationUtil validator, ModelMapper modelMapper, XmlParser xmlParser) {
        this.productRepository = productRepository;
        this.branchRepository = branchRepository;
        this.fileUtil = fileUtil;
        this.validator = validator;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean productsAreImported() {
        return productRepository.count() > 0;
    }

    @Override
    public String readProductsXmlFile() throws IOException {
        return fileUtil.readFile(PRODUCTS_XML_FILE_PATH);
    }

    @Override
    public String importProducts() throws JAXBException {
        ProductRootDto productRootDto = xmlParser.parseXml(ProductRootDto.class, PRODUCTS_XML_FILE_PATH);
        List<String> result = new ArrayList<>();
        for (ProductDto productDto : productRootDto.getProductDtos()) {
            Branch branch = branchRepository.findByName(productDto.getBranch()).orElse(null);
            if (branch==null||!validator.isValid(productDto)){
                result.add("Error: Invalid data.");
                continue;
            }
            Product product = modelMapper.map(productDto, Product.class);
            product.setBranch(branch);
            productRepository.saveAndFlush(product);
            result.add(String.format("Successfully imported Product %s.", product.getName()));
        }
        return String.join("\n", result);
    }
}
