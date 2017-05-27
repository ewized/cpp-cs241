/*
  Joshua Rodriguez
  5/27/2017
  CS241
 */
import java.util.Objects;

public class Road {
  private int fromId;
  private int toId;
  private int distance;

  /** Create the city from the line */
  public static Road of(String line) {
    String[] lines = parse(line);
    //System.out.println(Arrays.asList(lines));
    return new Road(
      Integer.parseInt(lines[0]), // fromId
      Integer.parseInt(lines[1]), // toId
      Integer.parseInt(lines[2]) // distance
    );
  }

  /** Clean the line to allow the parser to read it */
  private static String[] parse(String line) {
    line = Objects.requireNonNull(line).replaceAll("( )+", "-");
    if (line.charAt(0) == '-') {
      line = line.substring(1);
    }
    String[] lines = line.split("-");
    return lines;
  }

  public Road(int fromId, int toId, int distance) {
    this.fromId = fromId;
    this.toId = toId;
    this.distance = distance;
  }

  /** The from id of the city */
  public int fromId() {
    return fromId;
  }

  /** The to id of the city */
  public int toId() {
    return toId;
  }

  /** The distance between the two cities id */
  public int distance() {
    return distance;
  }
}
