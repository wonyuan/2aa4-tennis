package ca.mcscert.se2aa4.demos.tennis.scorer;

import ca.mcscert.se2aa4.demos.tennis.Player;
import java.util.Optional;

public class RuleBasedSystem implements ScoreSystem {

    private final Player p1;
    private final Player p2;

    private STATUS p1Status = STATUS.LOVE;
    private STATUS p2Status = STATUS.LOVE;

    public RuleBasedSystem(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public Optional<Player> winner() {
        if (p1Status == STATUS.GAME)
            return Optional.of(p1);
        if (p2Status == STATUS.GAME)
            return Optional.of(p2);
        return Optional.empty();
    }

    @Override
    public void score(Player p) {
        if (p.equals(p1)) {
            p1Status = computeNextStatus(p1Status, p2Status);
        } else if (p.equals(p2)) {
            p2Status = computeNextStatus(p2Status, p1Status);
        } else {
            throw new IllegalArgumentException("Unknown player " + p);
        }
        processDeuce(); // need to reset scores to FORTY - FORTY if DEUCE.
    }

    /**
     * Compute the next score for a player, considering their opponent's score.
     * @param mine current player's score
     * @param opponent opponent's score
     * @return the next score, or DEUCE if the game reach this states.
     */
    private STATUS computeNextStatus(STATUS mine, STATUS opponent) {
        switch (mine) {
            case LOVE, FIFTEEN, THIRTY -> { return mine.next(); }
            case FORTY -> {
                switch (opponent) {
                    case LOVE, FIFTEEN, THIRTY -> { return STATUS.GAME; }
                    case FORTY -> { return STATUS.ADVANTAGE; }
                    case ADVANTAGE -> { return STATUS.DEUCE; }
                }
            }
            case ADVANTAGE -> { return STATUS.GAME; }
        }
        throw new IllegalStateException("Impossible to compute next status [mine: " +
                mine + ", opponent: " + opponent + "]");
    }

    /**
     * Cleanup method that resets both scores to FORTY if the game reaches a DEUCE state.
     */
    private void processDeuce() {
        if (p1Status == STATUS.DEUCE || p2Status == STATUS.DEUCE) {
            p1Status = STATUS.FORTY;
            p2Status = STATUS.FORTY;
        }
    }

    @Override
    public String status() {
        String status = p1Status.name() + " - " + p2Status.name();
        if (p1Status == STATUS.FORTY && p2Status == STATUS.FORTY)
            status += " (DEUCE)";
        return status;
    }


    /**
     * Internal enum modelling the scores AND the deuce state (as such, a status more than a score)
     */
    private static enum STATUS {
        LOVE, FIFTEEN, THIRTY, FORTY, GAME, ADVANTAGE, DEUCE;

        /**
         * Partition the space of regular scores (Love, 15, 30) and others (particular conditions)
         * @return true if this is a regular score, false elsewhere.
         */
        public boolean isRegularScore() {
            return switch (this) {
                case LOVE, FIFTEEN, THIRTY -> true;
                default -> false;
            };
        }

        /**
         * Move a regular status (score) to its next value (Love -> 15, 15 -> 30, 30 -> 40).
         * @return the next value, an exception if "this" is not a regular score
         */
        public STATUS next() {
            return switch (this) {
                case LOVE    -> FIFTEEN;
                case FIFTEEN -> THIRTY;
                case THIRTY  -> FORTY;
                default -> throw new IllegalStateException("No next() for " + this.name());
            };
        }

    }
}
