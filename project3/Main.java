/*
  Joshua Rodriguez
  5/27/2017
  CS241
 */
import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public class Main {
  private static final int SIZE = 21;
  private boolean running = true;
  // Create the map of the menu methods
  private static final Map<String, Consumer<Main>> MENU = new HashMap<>();
  static {
    MENU.put("!", Main::debug);
    MENU.put("Q", Main::query);
    MENU.put("D", Main::distance);
    MENU.put("I", Main::insert);
    MENU.put("R", Main::remove);
    MENU.put("H", Main::help);
    MENU.put("E", Main::exit);
  }

  private Map<String, City> map = new HashMap<>(); // codes to city objects
  private Graph<City> graph = new Graph<>(SIZE); // there are only 20 cities

  public Main() {
    try {
      Scanner cities = new Scanner(new File("city.dat"));
      Scanner roads = new Scanner(new File("road.dat"));
      while (cities.hasNextLine()) { // input all the cities in the graph
        String line = cities.nextLine();
        if (line.length() >= 10) { // there must be at lease one char for each category
          City city = City.of(line);
          graph.label(city.id(), city);
          map.put(city.code(), city);
        }
      }
      while (roads.hasNextLine()) { // connect all the cities together
        String line = roads.nextLine();
        if (line.length() >= 5) {
          Road road = Road.of(line); // must be at least 1 char for each row
          graph.addEdge(road.fromId(), road.toId(), road.distance());
        }
      }
    } catch (Exception error) {
      System.err.println("Could not read input files...");
      System.err.println(new File(".").getAbsolutePath());
      error.printStackTrace();
    }
  }

  /** Print out the output of the city */
  public void query() {
    System.out.print("City code: ");
    String code = new Scanner(System.in).next().toUpperCase();
    City city = map.get(code);
    if (city != null) {
      System.out.println(city);
    } else {
      System.out.println(String.format("There was no city by the code: %s", code));
    }
  }

  /** Insert a road between two cities */
  public void insert() {
    System.out.print("City codes and distance: ");
    String[] parts = new Scanner(System.in).nextLine().toUpperCase().split(" ");
    if (parts.length < 2) {
      System.out.println("Invalid arguments, must supply two city codes.");
      return;
    }
    City from = map.get(parts[0]);
    City to = map.get(parts[1]);
    if (from == null || to == null) {
      System.out.println("Could not find one of the cities by the code.");
      return;
    }
    if (parts.length != 3) {
      System.out.println("No distance given, using 0 by default.");
    }
    int distance = parts.length > 2 ? Integer.parseInt(parts[2]) : 0;
    if (graph.isEdge(from.id(), to.id())) {
      System.out.println(String.format("You have replaced a road from %s to %s with a distance of %d.", from.name(), to.name(), distance));
    } else {
      System.out.println(String.format("You have inserted a road from %s to %s with a distance of %d.", from.name(), to.name(), distance));
    }
    graph.addEdge(from.id(), to.id(), distance);
  }

  /** Remove a road between two cities */
  public void remove() {
    System.out.print("City codes and distance: ");
    String[] parts = new Scanner(System.in).nextLine().toUpperCase().split(" ");
    if (parts.length != 2) {
      System.out.println("Invalid arguments, must supply two city codes.");
      return;
    }
    City from = map.get(parts[0]);
    City to = map.get(parts[1]);
    if (from == null || to == null) {
      System.out.println("Could not find one of the cities by the code.");
      return;
    }
    if (graph.isEdge(from.id(), to.id())) {
      graph.removeEdge(from.id(), to.id());
    } else {
      System.out.println(String.format("The road from %s and %s PASS doesn't exist.", from.name(), to.name()));
    }
  }

  /** Find the min distance */
  public void distance() {
    System.out.print("City codes: ");
    String[] parts = new Scanner(System.in).nextLine().toUpperCase().split(" ");
    if (parts.length != 2) {
      System.out.println("Invalid arguments, must supply two city codes.");
      return;
    }
    City starting = map.get(parts[0]);
    City ending = map.get(parts[1]);
    if (starting == null || ending == null) {
      System.out.println("Could not find one of the cities by the code.");
      return;
    }
    // dijistra
    Stack<City> cities = graph.minPath(starting.id(), ending.id());
    if (cities.size() == 0) {
      System.out.println(String.format("No shortest path between %s and %s.", starting.name(), ending.name()));
      return;
    }
    int distance = 0;
    City city = cities.pop();
    int last = city.id();
    String codes = city.code();
    while (!cities.isEmpty()) {
      city = cities.pop();
      codes += ", " + city.code();
      int tmp = city.id();
      distance += graph.weight(last, tmp);
      last = tmp;
    }
    System.out.println(String.format("The minimum distance between %s and %s is %d through the route: %s.", starting.name(), ending.name(), distance, codes));
  }

  /** Help debug the output */
  public void debug() {
    System.out.println(this.graph);
  }

  /** Print out the help message */
  public void help() {
    System.out.println("Q Query the city information by entering the city code.");
    System.out.println("D Find the minimum distance between two cities.");
    System.out.println("I Insert a road by entering two city codes and distance.");
    System.out.println("R Remove an existing road by entering two city codes.");
    System.out.println("H Display this message.");
    System.out.println("E Exit.");
  }

  /** Exit the program set the menu loop to exit */
  public void exit() {
    running = false;
  }

  /** The main logic of the driver */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("% java Project3");
    Main main = new Main();
    // Loop the menu while the program is still running
    while (main.running) {
      System.out.print("Command? ");
      String rawLine = in.nextLine();
      String[] cmdArgs = rawLine.split(" ");
      String key = cmdArgs[0].toUpperCase(); // Support lowercase menu items
      if (cmdArgs.length >= 1 && MENU.containsKey(key)) {
        MENU.get(key).accept(main);
      } else {
        System.out.println("No command found.");
      }
    }
  }
}
