/*
  Joshua Rodriguez
  5/27/2017
  CS241
 */

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/** Graph implementation using bit wise logic */
public class Graph<T> {
  public static final int MAX_SIZE = 32;
  private int[] edges;
  private T[] labels;
  private int[][] weights;

  /** Default to the max size the graph can handle */
  public Graph() {
    this(MAX_SIZE); // default to ints bit size
  }

  /** Create the size of the graph */
  public Graph(int n) {
    if (n > MAX_SIZE) {
      throw new RuntimeException("You can only have a max graph of 32 with this implementation");
    }
    edges = new int[n];
    weights = new int[n][n];
    labels = (T[]) new Object[n];
  }

  /** Checks if there is an edge between src and target */
  public boolean isEdge(int src, int target) {
    return (edges[src] | (0x01 << target)) == edges[src];
  }

  /** Add the specific edge to the graph */
  public void addEdge(int src, int target) {
    addEdge(src, target, 0);
  }

  /** Add the specific edge to the graph with a weight */
  public void addEdge(int src, int target, int weight) {
    edges[src] |= (0x01 << target);
    weights[src][target] = weight;
  }

  /** Removes the specific edge from the graph */
  public void removeEdge(int src, int target) {
    edges[src] &= ~(0x01 << target);
  }

  /** Set the label at the index */
  public void label(int i, T label) {
     labels[i] = label;
  }

  /** Get the label at the index */
  public T label(int i) {
    return labels[i];
  }

  /** Get the weight for the path */
  public int weight(int src, int target) {
    return weights[src][target];
  }

  /** Get all the labels in the graph */
  public Collection<? extends T> labels() {
    return Arrays.asList(labels);
  }

  /** Get all the neighbors for the label */
  public int[] neighbors(int label) {
    int[] tmp = new int[edges.length];
    int count = 0;
    for (int i = 0 ; i < edges.length; i++) {
      if (isEdge(label, i)) {
        tmp[count++] = i;
      }
    }
    return tmp;
  }

  /** Calculate the min path from src to the target */
  public Stack<T> minPath(int src, int target) {
    int[] distances = new int[edges.length];
    Map<Integer, Integer> map = new HashMap<>();
    Queue<Integer> priority = new PriorityQueue<>(Comparator.comparingInt(a -> distances[a]));
    Arrays.fill(distances, Integer.MAX_VALUE);
    distances[src] = 0;
    IntStream.range(0, labels.length).forEach(priority::add);
    while (!priority.isEmpty()) {
      int current = priority.poll();
      if (distances[current] == Integer.MAX_VALUE) {
        break;
      }
      for (int neighbor : neighbors(current)) {
        int size = distances[current] + weight(current, neighbor);
        if (priority.contains(neighbor) && size < distances[neighbor]) {
          distances[neighbor] = size;
          map.put(neighbor, current);
          priority.remove(neighbor);
          priority.add(neighbor);
        }
      }
    }
    // Create the stack the with template var
    Stack<T> list = new Stack<>();
    list.add(label(target));
    while (target != src) { // Add all the path routes to the stack to return it
      if (map.containsKey(target)) {
        target = map.get(target);
        list.add(label(target));
      } else {
        return new Stack<>(); // return empty stack for no distances
      }
    }
    return list;
  }

  @Override
  public String toString() {
    String block = "";
    for (int i = 0 ; i < labels.length; i++) {
      String x = Integer.toBinaryString(edges[i]);
      String y = "";
      for (int j = 0; j < labels.length - x.length(); j++) {
        y += "0";
      }
      block += i + ":\t" + y + x + "\n";
    }
    return block;
  }
}
