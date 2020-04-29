package bills_payment_system_database;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankAccount extends BillingDetail{
    @Column(name = "name")
    private String name;
    @Column(name = "swift_code")
    private String SWIFTcode;

    public BankAccount() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSWIFTcode() {
        return SWIFTcode;
    }

    public void setSWIFTcode(String SWIFTcode) {
        this.SWIFTcode = SWIFTcode;
    }
}
