package bills_payment_system_database;

import javax.persistence.*;

@Entity
@Table(name = "billing_details")
@DiscriminatorColumn(name = "type")
public abstract class BillingDetail extends BaseEntity{
    @Column(name = "number")
    private int number;
    @ManyToOne(targetEntity = User.class)
    private User owner;

    public BillingDetail() {
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
