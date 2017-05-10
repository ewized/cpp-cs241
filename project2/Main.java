/*
  Joshua Rodriguez
  5/10/2017
  Project 2
 */
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
  public static final int MAX_SIZE = 100;

  /** Compute random numbers from 1 - maxSize */
  public static List<Integer> randomNumbers(int maxSize) {
    List<Integer> tracker = new ArrayList<>(maxSize);
    Random rand = new Random();
    while (tracker.size() < maxSize) {
      int tmp = rand.nextInt(maxSize) + 1;
      if (!tracker.contains(tmp)) {
        tracker.add(tmp);
      }
    }
    return tracker;
  }

  /** Generate sorted list of numbers from 1 - maxSize */
  public static List<Integer> sortedNumbers(int maxSize) {
    List<Integer> tracker = new ArrayList<>(maxSize);
    for (int i = 1 ; i <= maxSize ; i++) {
      tracker.add(i);
    }
    return tracker;
  }

  /** Print the collection delimited by a comma only the first 10 items */
  public static void printCollection(Collection<? extends Number> collection) {
    System.out.println(collection.stream().map(Object::toString).limit(10).collect(Collectors.joining(", ")) + "...");
  }

  /** Run option one */
  public static void optionOne() {
    final int COUNT = 20;
    int avgSeries = 0;
    int avgOptimal = 0;
    for (int i = 0; i < COUNT; i++) {
      HeapTree series = new HeapTree();
      randomNumbers(MAX_SIZE).forEach(series::add);
      avgSeries += series.numberOfSwaps();
      HeapTree optimal = new HeapTree(randomNumbers(MAX_SIZE).stream().mapToInt(j -> j).toArray());
      avgOptimal += optimal.numberOfSwaps();
    }
    System.out.println("Average swaps for series of insertions: " + avgSeries / COUNT);
    System.out.println("Average swaps for optimal method: " + avgOptimal / COUNT);

  }

  /** Run option two */
  public static void optionTwo() {
    // logic for the series method
    HeapTree series = new HeapTree();
    sortedNumbers(MAX_SIZE).forEach(series::add);
    System.out.print("Heap built using series of insertions: ");
    printCollection(series.data());
    System.out.println("Number of swaps: " + series.numberOfSwaps());
    IntStream.range(0, 10).forEach(i -> series.remove());
    System.out.print("Heap after 10 removals: ");
    printCollection(series.data());

    System.out.println();

    // logic for the optimal method
    HeapTree optimal = new HeapTree(sortedNumbers(MAX_SIZE).stream().mapToInt(j -> j).toArray());
    System.out.print("Heap built using optimal method: ");
    printCollection(optimal.data());
    System.out.println("Number of swaps: " + optimal.numberOfSwaps());
    IntStream.range(0, 10).forEach(i -> optimal.remove());
    System.out.print("Heap after 10 removals: ");
    printCollection(optimal.data());
  }

  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("=====================================================================");
    System.out.println("Please select how to test the program:");
    System.out.println("(1) 20 sets of 100 randomly generated integers");
    System.out.println("(2) Fixed integer values 1-100");
    System.out.print("Enter choice: ");
    try {
      int number = in.nextInt();
      System.out.println();
      if (number == 1) { // Random Integers
        optionOne();
      } else if (number == 2) { // Sorted numbers
        optionTwo();
      } else {
        System.err.println("ID10T: You entered a number not in the list!!!");
      }
    } catch (InputMismatchException error) {
      System.err.println("ID10T: You did not even enter a number!!!");
    }
  }
}
