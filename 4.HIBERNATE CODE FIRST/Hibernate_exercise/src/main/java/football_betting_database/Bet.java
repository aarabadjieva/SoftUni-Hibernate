package football_betting_database;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "bet")
public class Bet extends BaseEntity{
    @Column(name = "bet_money")
    private Double betMoney;
    @Column(name = "date_and_time")
    private Date dateAndTimeOfBet;
    @ManyToOne(targetEntity = User.class)
    private User user;

    public Bet() {
    }

    public Double getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(Double betMoney) {
        this.betMoney = betMoney;
    }

    public Date getDateAndTimeOfBet() {
        return dateAndTimeOfBet;
    }

    public void setDateAndTimeOfBet(Date dateAndTimeOfBet) {
        this.dateAndTimeOfBet = dateAndTimeOfBet;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
