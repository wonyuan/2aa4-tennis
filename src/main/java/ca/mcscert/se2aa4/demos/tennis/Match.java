package ca.mcscert.se2aa4.demos.tennis;

public class Match {

    public static final String P1_NAME = "p1";
    public static final String P2_NAME = "p2";

    public Match(Integer integer, Integer integer1) { }

    public String play() {
        ScoreSystem scorer = new ScoreSystem();
        while(! scorer.isEnded()) {
            String who = decideGameWinner();
            scorer.score(who);
        }
        return scorer.winner()
                .orElseThrow(() -> new IllegalStateException("No Winner!"));
    }

    private String decideGameWinner() {
        System.out.println("Winning this game: " + P1_NAME);
        return P1_NAME;
    }

}
