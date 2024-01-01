package ca.mcscert.se2aa4.demos.tennis;

import ca.mcscert.se2aa4.demos.tennis.scorer.FiniteStateMachine;
import ca.mcscert.se2aa4.demos.tennis.scorer.RuleBasedSystem;

import ca.mcscert.se2aa4.demos.tennis.scorer.ScoreSystem;
import java.util.Random;

/**
 * Play a Tennis match between two players, p1 and p2;
 */
public class Match {

    private static final String P1_NAME = "p1";
    private static final String P2_NAME = "p2";

    private final Player player1;
    private final Player player2;

    private final ScoreSystem scorer;

    /**
     * Create a match, considering the strengths provided in the user-provided configuration
     * @param strengthP1 strength for the first player
     * @param strengthP2 strength for the second player
     * @param scoreSystem mnemonic used to select the score system (in {fsm, rule})
     */
    public Match(Integer strengthP1, Integer strengthP2, String scoreSystem) {
        this.player1 = new Player(P1_NAME, strengthP1);
        this.player2 = new Player(P2_NAME, strengthP2);
        this.scorer = switch(scoreSystem) {
            case "fsm"  -> new FiniteStateMachine(player1, player2);
            case "rule" -> new RuleBasedSystem(player1, player2);
            default -> throw new IllegalArgumentException("Unknown score system: " + scoreSystem);
        };
    }

    /**
     * Play a match (a sequence of games) until it is ended
     * @return the winner
     */
    public Player play() {
        System.out.println("Playing a match between " + player1 + " and " + player2);
        while( !scorer.isEnded() ) {
            this.playGame();
        }
        return scorer.winner().get();
    }

    /**
     * Play a game (decide who is the winner based on strengths)
     */
    private void playGame() {
        Double factor = (new Random()).nextDouble();
        double strength1 = player1.getStrength() * factor;
        double strength2 = player2.getStrength() * (1 - factor);
        Player winner = (strength1 > strength2 ? player1 : player2);
        String message = String.format("%-21s", scorer.status()) + " --[" + winner + "]--> ";
        scorer.score(winner);
        System.out.println(message + scorer.status());
    }


}
