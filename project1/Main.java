/*
  Joshua Rodriguez
  4/19/2017
  CS241
 */
import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Main {
  private IntBinarySearchTree tree;
  private final int[] values;
  private boolean running = true;
  // Create the map of the menu methods
  private static final Map<String, BiConsumer<Main, String[]>> MENU = new HashMap<>();
  static {
    MENU.put("I", Main::insert);
    MENU.put("D", Main::delete);
    MENU.put("P", Main::predecessor);
    MENU.put("S", Main::successor);
    MENU.put("E", Main::exit);
    MENU.put("H", Main::help);
  }

  /** Convert the string values to intergers */
  private Main(String[] values) {
    this(Stream.of(Objects.requireNonNull(values, "Values can not be null")).mapToInt(Integer::valueOf).toArray());
  }

  /** Create the instance of the raw values */
  public Main(int[] values) {
    this.values = Objects.requireNonNull(values, "Values can not be null");
    this.tree = new IntBinarySearchTree(values);
  }

  /** Insert a value */
  private static void insert(Main main, String[] args) {
    try {
      main.tree.add(Integer.valueOf(args[0]));
      main.printInOrder();
    } catch (IllegalArgumentException error) {
      System.out.println(error.getMessage());
    }
  }

  /** Delete a value */
  private static void delete(Main main, String[] args) {
    try {
      main.tree = main.tree.remove(Integer.valueOf(args[0]));
      main.printInOrder();
    } catch (IllegalArgumentException error) {
      System.out.println(error.getMessage());
    }
  }

  /** Find predecessor */
  private static void predecessor(Main main, String[] args) {
    int value = Integer.valueOf(args[0]);
    Stack<IntBinarySearchTree> stack = new Stack<>();
    main.tree.traverseInorder(node -> {
      stack.push(node);
      if (node.value() == value && stack.size() > 1) {
        stack.pop();
        System.out.println(stack.pop().value());
      }
    });
  }

  /** Find successor */
  private static void successor(Main main, String[] args) {
    int value = Integer.valueOf(args[0]);
    Stack<IntBinarySearchTree> stack = new Stack<>();
    main.tree.traverseInorder(node -> {
      stack.push(node);
      IntBinarySearchTree tmp = stack.pop();
      if (stack.size() >= 1 && stack.peek().value() == value) {
        System.out.println(tmp.value());
        stack.pop(); // remove to prevent dupe findings
      } else {
        stack.push(node);
      }
    });
  }

  /** Exit this program */
  private static void exit(Main main, String[] args) {
    main.running = false;
    System.out.println("Thank you for using my program!");
  }

  /** Print the menu system */
  private static void help(Main main, String[] args) {
    System.out.println("I Insert a value");
    System.out.println("D Delete a value");
    System.out.println("P Find predecessor");
    System.out.println("S Find successor");
    System.out.println("E Exit the program");
    System.out.println("H Display this message");
  }

  private void printPreOrder() {
    System.out.print("Pre-order:");
    tree.traversePreorder(node -> System.out.print(" " + node.value()));
    System.out.println();
  }

  private void printInOrder() {
    System.out.print("In-order:");
    tree.traverseInorder(node -> System.out.print(" " + node.value()));
    System.out.println();
  }

  private void printPostOrder() {
    System.out.print("Post-order:");
    tree.traversePostorder(node -> System.out.print(" " + node.value()));
    System.out.println();
  }

  /** The main logic of the driver */
  public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
    System.out.println("% java Project1");
    System.out.println("Please enter the initial sequence of values:");
    Main main = new Main(in.nextLine().split(" "));
    // Print the tree in pre order
    main.printPreOrder();
    // Print the tree in in order
    main.printInOrder();
    // Print the tree in post order
    main.printPostOrder();
    // Loop the menu while the program is still running
    while (main.running) {
      System.out.print("Command? ");
      String rawLine = in.nextLine();
      String[] cmdArgs = rawLine.split(" ");
      String key = cmdArgs[0].toUpperCase(); // Support lowercase menu items
      if (cmdArgs.length >= 1 && MENU.containsKey(key)) {
        MENU.get(key).accept(main, Arrays.copyOfRange(cmdArgs, 1, cmdArgs.length));
      } else {
        System.out.println("No command found.");
      }
    }
    System.out.print("%");
  }
}
