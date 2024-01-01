package ca.mcscert.se2aa4.demos.tennis;

import ca.mcscert.se2aa4.demos.tennis.scorer.ScoreSystem;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class for the Tennis Counting System: entry point & configuration.
 * Usage: -p1 p1_strength -p2 p2_strength
 */
public class Main {

    /**
     * Entry point for the system
     * @param args command line arguments provided by the user
     */
    public static void main(String[] args) {
        try {
            System.out.println("** Starting Tennis Counter Assistant");
            Configuration config = configure(args);
            System.out.println("**** Starting game");
            Match theMatch =
                    new Match(config.p1Strength(), config.p2Strength(), config.scoreSystem());
            Player winner = theMatch.play();
            System.out.println("==>> Winner: " + winner);
            System.out.println("** Closing Tennis Counter Assistant");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Read arguments using Apache CLI library.
     * If not provided, the strength of a player defaults to 50.
     * @param commandLineArguments the arguments received from the user
     * @return a Configuration with the "strength" of each player (integer in [0,100])
     * @throws ParseException if command line is invalid
     */
    private static Configuration configure(String[] commandLineArguments) throws ParseException {
        System.out.println("**** Reading Command-Line Arguments");
        Options options = new Options();
        options.addOption("p1", true, "Strength of Player 1 in [0,100]");
        options.addOption("p2", true, "Strength of Player 2 in [0,100]");
        options.addOption("scorer", true, "Score system to use (rule, fsm)");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, commandLineArguments);
        Integer p1 = Integer.parseInt(cmd.getOptionValue("p1","50"));
        System.out.println("****** P1's Strength is " + p1 + "/100");
        Integer p2 = Integer.parseInt(cmd.getOptionValue("p2","50"));
        System.out.println("****** P2's Strength is " + p2 + "/100");
        String system = cmd.getOptionValue("scorer", "rule");
        return new Configuration(p1, p2, system);
    }

    /**
     * Data class (record) to store players' strengths.
     * @param p1Strength integer in [0,100]
     * @param p2Strength integer in [0,100]
     * @param scoreSystem the score system to use
     */
    private record Configuration(Integer p1Strength, Integer p2Strength, String scoreSystem) {
        Configuration { // Validating data input
            if (p1Strength < 0 || p1Strength > 100)
                throw  new IllegalArgumentException("P1's Strength not in [0,100]: " + p1Strength);
            if (p2Strength < 0 || p2Strength > 100)
                throw  new IllegalArgumentException("P2's Strength not in [0,100]: " + p2Strength);
        }
    }

}
