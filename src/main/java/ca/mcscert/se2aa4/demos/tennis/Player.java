package ca.mcscert.se2aa4.demos.tennis;

import java.util.Objects;

/**
 * Model what a Player is for the Tennis game (basically a name and a strength).
 */
public class Player {

    private final String name;
    private final Integer strength;

    /**
     * Build a player to be used while playing tennis.
     * @param name the name of the player
     * @param strength their strength
     */
    public Player(String name, Integer strength) {
        this.name = name;
        this.strength = strength;
    }

    public Integer getStrength() { return this.strength; }

    @Override
    public String toString() { return name; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(name, player.name) && Objects.equals(strength, player.strength);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, strength);
    }

}
