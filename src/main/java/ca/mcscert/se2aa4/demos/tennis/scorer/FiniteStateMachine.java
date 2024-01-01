package ca.mcscert.se2aa4.demos.tennis.scorer;

import ca.mcscert.se2aa4.demos.tennis.Player;
import java.util.Optional;

/**
 * This class implements the complete Finite State Machine (FSM)  that models a tennis match.
 * Source: https://blog.ploeh.dk/2021/03/29/table-driven-tennis-scoring/
 */
public class FiniteStateMachine implements ScoreSystem {
    // source:

    private STATE currentState = STATE.LoveAll;
    private final Player p1;
    private final Player p2;

    public FiniteStateMachine(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Optional<Player> winner() {
        return switch (currentState) {
            case GamePlayerOne -> Optional.of(p1);
            case GamePlayerTwo -> Optional.of(p2);
            default -> Optional.empty();
        };
    }

    @Override
    public String status() {
        return this.currentState.toString();
    }

    public void score(Player p) {
        if (p1.equals(p)) {
            scorePlayerOne();
        } else if(p2.equals(p)) {
            scorePlayerTwo();
        } else {
            throw new IllegalArgumentException("Unknown player " + p);
        }
    }

    /**
     * Transition table when Player 1 scores
     */
    private void scorePlayerOne() {
        this.currentState = switch (currentState) {
            case GamePlayerOne, GamePlayerTwo
                    -> throw new IllegalArgumentException("Game already won!");
            case FortyLove, FortyFifteen, FortyThirty, AdvantagePlayerOne
                    -> STATE.GamePlayerOne;
            case ThirtyForty, AdvantagePlayerTwo
                    -> STATE.Deuce;

            case LoveAll       -> STATE.FifteenLove;
            case FifteenLove   -> STATE.ThirtyLove;
            case ThirtyLove    -> STATE.FortyLove;

            case LoveFifteen   -> STATE.FifteenAll;
            case LoveThirty    -> STATE.FifteenThirty;
            case LoveForty     -> STATE.FifteenForty;

            case FifteenAll    -> STATE.ThirtyFifteen;
            case FifteenThirty -> STATE.ThirtyAll;
            case FifteenForty  -> STATE.ThirtyForty;

            case ThirtyFifteen -> STATE.FortyFifteen;
            case ThirtyAll     -> STATE.FortyThirty;

            case Deuce         -> STATE.AdvantagePlayerOne;
        };
    }

    /**
     * Transition table when Player 2 scores
     */
    private void scorePlayerTwo() {
        this.currentState = switch (currentState) {
            case GamePlayerOne, GamePlayerTwo
                    -> throw new IllegalArgumentException("Game already won!");
            case LoveForty, FifteenForty, ThirtyForty, AdvantagePlayerTwo
                    -> STATE.GamePlayerTwo;
            case FortyThirty, AdvantagePlayerOne
                    -> STATE.Deuce;

            case LoveAll       -> STATE.LoveFifteen;
            case LoveFifteen   -> STATE.LoveThirty;
            case LoveThirty    -> STATE.LoveForty;

            case FifteenLove   -> STATE.FifteenAll;
            case ThirtyLove    -> STATE.ThirtyFifteen;
            case FortyLove     -> STATE.FortyFifteen;

            case FifteenAll    -> STATE.FifteenThirty;
            case ThirtyFifteen -> STATE.ThirtyAll;
            case FortyFifteen  -> STATE.FortyThirty;

            case FifteenThirty -> STATE.FifteenForty;
            case ThirtyAll     -> STATE.ThirtyForty;

            case Deuce -> STATE.AdvantagePlayerTwo;

        };
    }

    /**
     * Internal enumeration for all the possible states of the match
     */
    private enum STATE {
        LoveAll("Love - Love"), FifteenLove("15 - Love"),
        LoveFifteen("Love - 15"), ThirtyLove("30 - Love"),
        FifteenAll("15 - 15"), LoveThirty("Love - 30"),
        FortyLove("40 - Love"), ThirtyFifteen("30 - 15"),
        FifteenThirty("15 - 30"), LoveForty("Love - 40"),
        FortyFifteen("40 - 15"), ThirtyAll("30 - 30"),
        FifteenForty("15 - 40"), GamePlayerOne("Game: Player 1"),
        FortyThirty("40 - 30"), ThirtyForty ("30 - 40"),
        GamePlayerTwo("Game: Player 2"),  AdvantagePlayerOne("Advantage: Player 1"),
        Deuce("Deuce"), AdvantagePlayerTwo("Advantage: Player 2");

        private final String name;

        STATE(String name) { this.name = name; }

        @Override
        public String toString() { return name; }
    }

}
