package alararestaurant.domain.entities;

import alararestaurant.domain.entities.enums.OrderType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Column(name = "customer")
    @NotNull(message = "Order customer cannot be null")
    private String customer;

    @Column(name = "date_time")
    @NotNull(message = "Order date and time cannot be null")
    private LocalDateTime dateTime;

    @Enumerated(value = EnumType.STRING)
    @NotNull(message = "Order type cannot be null")
    private OrderType type;

    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;

    @OneToMany(mappedBy = "order")
    private List<OrderItem> orderItems;

    public Order() {
        this.type = OrderType.ForHere;
        this.orderItems = new ArrayList<>();
    }
}
