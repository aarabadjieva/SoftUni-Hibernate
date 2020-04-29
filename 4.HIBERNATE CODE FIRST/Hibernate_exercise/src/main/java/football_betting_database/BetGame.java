package football_betting_database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "bets_games")
public class BetGame implements Serializable {

    @Id
    @OneToOne(targetEntity = Game.class)
    private Game game;
    @Id
    @OneToOne(targetEntity = Bet.class)
    private Bet bet;
    @OneToOne
    @JoinColumn(name = "result_prediction")
    private ResultPrediction resultPrediction;

    public BetGame() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Bet getBet() {
        return bet;
    }

    public void setBet(Bet bet) {
        this.bet = bet;
    }

    public ResultPrediction getResultPrediction() {
        return resultPrediction;
    }

    public void setResultPrediction(ResultPrediction resultPrediction) {
        this.resultPrediction = resultPrediction;
    }
}
