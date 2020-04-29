package alararestaurant.domain.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem extends BaseEntity{

    @Column(name = "quantity")
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1, message = "Quantity must be minimum 1")
    private int quantity;

    @ManyToOne(optional = false)
    @JoinColumn(name = "item_id", referencedColumnName = "id")
    private Item item;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    public OrderItem(int quantity, Item item) {
        this.quantity = quantity;
        this.item = item;
    }
}
