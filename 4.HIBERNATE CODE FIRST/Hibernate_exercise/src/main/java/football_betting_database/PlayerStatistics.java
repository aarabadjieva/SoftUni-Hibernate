package football_betting_database;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "players_statistics")
public class PlayerStatistics implements Serializable {
    @Id
    @ManyToOne(targetEntity = Game.class)
    private Game game;
    @Id
    @ManyToOne(targetEntity = Player.class)
    private Player player;
    @Column(name = "goals")
    private int scoredGoals;
    @Column(name = "assists")
    private int playerAssists;
    @Column(name = "minutes_played")
    private int minutesPlayed;

    public PlayerStatistics() {
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getScoredGoals() {
        return scoredGoals;
    }

    public void setScoredGoals(int scoredGoals) {
        this.scoredGoals = scoredGoals;
    }

    public int getPlayerAssists() {
        return playerAssists;
    }

    public void setPlayerAssists(int playerAssists) {
        this.playerAssists = playerAssists;
    }

    public int getMinutesPlayed() {
        return minutesPlayed;
    }

    public void setMinutesPlayed(int minutesPlayed) {
        this.minutesPlayed = minutesPlayed;
    }
}
