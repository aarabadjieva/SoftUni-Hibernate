package soft_uni.demo.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Address {
    private String city;

    public Address(String city) {
        this.city = city;
    }
}
