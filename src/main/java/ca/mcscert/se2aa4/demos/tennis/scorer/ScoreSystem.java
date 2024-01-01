package ca.mcscert.se2aa4.demos.tennis.scorer;

import ca.mcscert.se2aa4.demos.tennis.Player;
import java.util.Optional;

/**
 * Common abstractions provided by a score system
 */
public interface ScoreSystem {

    /**
     * Has the game reached a victory state?
     * This is syntactic sugar on top of the winner() method.
     * @return true if one player won, false elsewhere
     *
     */
    default boolean isEnded() {
        return winner().isPresent();
    }

    /**
     * Do we have a winner?
     * @return Empty is none, of(winner) is winner won the game.
     */
    Optional<Player> winner();

    /**
     * update scorer state considering that player p won this game.
     * @param p the player who just won the point.
     */
    void score(Player p);

    /**
     * Return the status of the score system
     * @return a string describing the status of the match so far.
     */
    String status();

}
