package ca.mcscert.se2aa4.demos.tennis;

import java.util.Optional;

public class ScoreSystem {

    private String winner = null;

    public void score(String playerName) {
        this.winner = playerName;
    }

    public boolean isEnded() {
        return winner != null;
    }

    public Optional<String> winner() {
        return (isEnded()? Optional.of(winner): Optional.empty());
    }

}
