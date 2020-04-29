package alararestaurant.service;

import alararestaurant.domain.dtos.importDto.ItemOrderDto;
import alararestaurant.domain.dtos.importDto.OrderImportDto;
import alararestaurant.domain.dtos.importDto.OrderRootDto;
import alararestaurant.domain.entities.Employee;
import alararestaurant.domain.entities.Item;
import alararestaurant.domain.entities.Order;
import alararestaurant.domain.entities.OrderItem;
import alararestaurant.repository.EmployeeRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.repository.OrderItemRepository;
import alararestaurant.repository.OrderRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import alararestaurant.util.XmlParser;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final static String ORDER_XML_FILE_PATH = "D:\\Programming\\6.Hibernate\\11.EXAM PREPARATION\\AlaraRestaurant\\src\\main\\resources\\files\\orders.xml";
    
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final EmployeeRepository employeeRepository;
    private final ItemRepository itemRepository;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validator;
    private final FileUtil fileUtil;
    private final XmlParser xmlParser;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, EmployeeRepository employeeRepository, ItemRepository itemRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validator, FileUtil fileUtil, XmlParser xmlParser) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.employeeRepository = employeeRepository;
        this.itemRepository = itemRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validator = validator;
        this.fileUtil = fileUtil;
        this.xmlParser = xmlParser;
    }

    @Override
    public Boolean ordersAreImported() {
        return this.orderRepository.count() > 0;
    }

    @Override
    public String readOrdersXmlFile() throws IOException {
        return fileUtil.readFile(ORDER_XML_FILE_PATH);
    }

    @Override
    public String importOrders() throws JAXBException {
        OrderRootDto orderRootDto= xmlParser.importXml(OrderRootDto.class, ORDER_XML_FILE_PATH);
        StringBuilder sb = new StringBuilder();
        for (OrderImportDto orderDto : orderRootDto.getOrders()) {
            Employee employee = employeeRepository.findByName(orderDto.getEmployee()).orElse(null);
            if (employee==null){
                continue;
            }
            LocalDateTime dateTime = LocalDateTime.parse(orderDto.getDateTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            boolean isPresentItem = true;
            List<OrderItem> orderItems = new ArrayList<>();
            for (ItemOrderDto itemDto : orderDto.getItemRootDto().getItems()) {
                Item item = itemRepository.findByName(itemDto.getName()).orElse(null);
                if (item==null){
                    isPresentItem = false;
                    continue;
                }
                OrderItem orderItem = new OrderItem(itemDto.getQuantity(), item);
                orderItems.add(orderItem);
            }
            if (!isPresentItem){
                continue;
            }
            Order order = modelMapper.map(orderDto, Order.class);
            order.setEmployee(employee);
            order.setDateTime(dateTime);
            order.setOrderItems(orderItems);
            if (!validator.isValid(order)){
                sb.append(validator.violations(order)).append(System.lineSeparator());
                continue;
            }
            orderRepository.saveAndFlush(order);
            for (OrderItem orderItem : orderItems) {
                orderItem.setOrder(order);
            }
            orderItemRepository.saveAll(order.getOrderItems());
            sb.append(String.format("Order for %s on %s added", order.getCustomer(), order.getDateTime()))
                    .append(System.lineSeparator());
        }
        return sb.toString();
    }

    @Override
    public String exportOrdersFinishedByTheBurgerFlippers() {
        StringBuilder sb = new StringBuilder();
        orderRepository.findAllByBurgerFlippersOrderedByEmployeeNameAndOrderId()
                .forEach(o->{
                    sb.append(String.format("Name: %s\n" +
                            "Orders:\n" +
                            "   Customer: %s\n" +
                            "   Items:\n", o.getEmployee().getName(),
                            o.getCustomer()));
                    o.getOrderItems().stream().forEach(i->{
                        sb.append(String.format("Name: %s\n" +
                                "      Price: %.2f\n" +
                                "      Quantity: %d\n", i.getItem().getName(),
                                i.getItem().getPrice(), i.getQuantity()))
                                .append(System.lineSeparator());
                    });
                });
        return sb.toString();
    }
}
