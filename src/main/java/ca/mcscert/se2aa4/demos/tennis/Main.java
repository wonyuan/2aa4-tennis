package ca.mcscert.se2aa4.demos.tennis;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) {
        try {
            Configuration config = configure(args);
            System.out.println(config);
            Match theMatch = new Match(config.p1Strength(), config.p2Strength());
            String winner = theMatch.play();
            System.out.println(winner);
        } catch (Exception e) {
            System.err.println("An error has occurred");
            e.printStackTrace();
            System.exit(1);
        }
    }

    private static Configuration configure(String[] args) throws ParseException {
        Options options = new Options();
        options.addOption("p1", true, "Strength of Player 1 in [0,100]");
        options.addOption("p2", true, "Strength of Player 2 in [0,100]");
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);
        Integer p1 = Integer.parseInt(cmd.getOptionValue("p1","50"));
        Integer p2 = Integer.parseInt(cmd.getOptionValue("p2","50"));
        return new Configuration(p1, p2);
    }

    private record Configuration(Integer p1Strength, Integer p2Strength) {
        Configuration {
            if (p1Strength < 0 || p1Strength > 100)
                throw  new IllegalArgumentException("P1's Strength not in [0,100]: " + p1Strength);
            if (p2Strength < 0 || p2Strength > 100)
                throw  new IllegalArgumentException("P2's Strength not in [0,100]: " + p2Strength);
        }
    }

}
