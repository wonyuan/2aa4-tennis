# Driver for Tennis Counting Demo

To get the code ready for demo:

```
mosser@azrael 2aa4-tennis % git checkout demo_step_0
```

Look at the repository:

- `README.md`: description of the project, and how to build and run it
- `LICENSE.txt`: under which condition can one reuse this project?
- `.gitignore`: what _NOT_ to record in the version control system
- `pom.xml`: building and packaging configuration, as well as dependency management and metadata
- `.github/workflows/build.yaml`: continuous integration pipeline to build and package the product each time we push to GitHub.
- `src`: the source code. (Remember: software engineering is not all about coding, even if coding is essential).

# Step #1: Fix the starter code structure

1. Create an  _immutable_ concept for `Configuration` inside `Main`


```
private record Configuration(Integer p1Strength, Integer p2Strength) {
    Configuration { 
        if (p1Strength < 0 || p1Strength > 100)
            throw  new IllegalArgumentException("P1's Strength not in [0,100]: " + p1Strength);
        if (p2Strength < 0 || p2Strength > 100)
            throw  new IllegalArgumentException("P2's Strength not in [0,100]: " + p2Strength);
    }
}
```

2. Extract the configuration code into a dedicated (private) method in `Main`

```
private static Configuration configure(String[] commandLineArguments) throws ParseException {
    Options options = new Options();
    options.addOption("p1", true, "Strength of Player 1 in [0,100]");
    options.addOption("p2", true, "Strength of Player 2 in [0,100]");
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, commandLineArguments);
    Integer p1 = Integer.parseInt(cmd.getOptionValue("p1","50"));
    Integer p2 = Integer.parseInt(cmd.getOptionValue("p2","50"));
    return new Configuration(p1, p2, system);
}
```

3. Modify `Main::main` to use this method

```
public static void main(String[] args) {
    try {
        Configuration config = configure(args);
        System.out.println(config);
        Match theMatch =
                new Match(config.p1Strength(), config.p2Strength());
        String winner = theMatch.play();
        System.out.println("==>> Winner: " + winner);
    } catch (Exception e) {
        System.err.println(e.getMessage());
        e.printStackTrace();
        System.exit(1);
    }
}
```

4. Use the IDE completion mechanisms to create `Match` and the missing method

```
public class Match {

    public static String P1_NAME = "p1";
    public static String P2_NAME = "p2";

    public Match(Integer integer, Integer integer1) { }

    public String play() {
        return "No winner yet!";
    }

}
```

We can now compile, try it out, commit and share.

```
mosser@azrael 2aa4-tennis % mvn -q clean package
mosser@azrael 2aa4-tennis % java -jar target/tennis.jar -p1 20 -p2 78
Configuration[p1Strength=20, p2Strength=78]
No winner yet!
mosser@azrael 2aa4-tennis % git status
On branch demo
Changes to be committed:
  (use "git restore --staged <file>..." to unstage)
        new file:   src/main/java/ca/mcscert/se2aa4/demos/tennis/Match.java

Changes not staged for commit:
  (use "git add <file>..." to update what will be committed)
  (use "git restore <file>..." to discard changes in working directory)
        modified:   src/main/java/ca/mcscert/se2aa4/demos/tennis/Main.java
        modified:   src/main/java/ca/mcscert/se2aa4/demos/tennis/Match.java

mosser@azrael 2aa4-tennis % git add -A
mosser@azrael 2aa4-tennis % git commit -m "first refactoring to move from starter to walking skeleton" 
mosser@azrael 2aa4-tennis % git tag "demo_step_01"
mosser@azrael 2aa4-tennis % git push --tags origin demo
```

What we've done here is a _refactoring_. We have not added any new feature in the code. We have changed the structure so that it is better from a technical point of view.

