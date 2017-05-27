/*
  Joshua Rodriguez
  5/27/2017
  CS241
 */
import java.util.Objects;

public class City {
  private int id;
  private String code;
  private String name;
  private int population;
  private int elevation;

  /** Create the city from the line */
  public static City of(String line) {
    String[] lines = parse(line);
    //System.out.println(Arrays.asList(lines));
    return new City(
      Integer.parseInt(lines[0]), // id
      lines[1], // code
      lines[2], // name
      Integer.parseInt(lines[3]), // population
      Integer.parseInt(lines[4]) // elevation
    );
  }

  /** Clean the line to allow the parser to read it */
  private static String[] parse(String line) {
    line = Objects.requireNonNull(line).replaceAll("([A-Z]) ([A-Z])", "$1-$2").replaceAll("([ ])+", " ");
    if (line.charAt(0) == ' ') {
      line = line.substring(1);
    }
    String[] lines = line.split(" ");
    lines[2] = lines[2].replace("-", " ");
    return lines;
  }

  public City(int id, String code, String name, int population, int elevation) {
    this.id = id;
    this.code = Objects.requireNonNull(code);
    this.name = Objects.requireNonNull(name);
    this.population = population;
    this.elevation = elevation;
  }

  /** Get the id of the city */
  public int id() {
    return id;
  }

  /** Get the code of the city */
  public String code() {
    return code;
  }

  /** Get the name of the city */
  public String name() {
    return name;
  }

  /** Get the population of the city */
  public int population() {
    return population;
  }

  /** Get the elevation of the city */
  public int elevation() {
    return elevation;
  }

  /** Print the city pretty */
  @Override
  public String toString() {
    return String.format("%d %s %s %d %d", id(), code(), name(), population(), elevation());
  }
}
