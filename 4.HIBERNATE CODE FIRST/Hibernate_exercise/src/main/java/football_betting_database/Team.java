package football_betting_database;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "teams")
public class Team extends BaseEntity {
    @Column(name = "name")
    private String name;
    @Lob
    private byte[] logo;
    @Column(name = "initials")
    private String initials;
    @ManyToOne(targetEntity = Color.class)
    @JoinColumn(name = "primary_kit_colour_id")
    private Color primaryKitColor;
    @ManyToOne(targetEntity = Color.class)
    @JoinColumn(name = "secondary_kit_color_id")
    private Color secondaryKitColor;
    @ManyToOne(targetEntity = Town.class)
    private Town town;
    @Column(name = "budget")
    private BigDecimal budget;
    @OneToMany(targetEntity = Player.class, mappedBy = "team")
    private Set<Player> players;

    public Team() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public Color getPrimaryKitColor() {
        return primaryKitColor;
    }

    public void setPrimaryKitColor(Color primaryKitColor) {
        this.primaryKitColor = primaryKitColor;
    }

    public Color getSecondaryKitColor() {
        return secondaryKitColor;
    }

    public void setSecondaryKitColor(Color secondaryKitColor) {
        this.secondaryKitColor = secondaryKitColor;
    }

    public Town getTown() {
        return town;
    }

    public void setTown(Town town) {
        this.town = town;
    }

    public BigDecimal getBudget() {
        return budget;
    }

    public void setBudget(BigDecimal budget) {
        this.budget = budget;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

}
