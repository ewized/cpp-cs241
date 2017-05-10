/*
  Joshua Rodriguez
  5/10/2017
  Project 2
 */
import java.util.ArrayList;
import java.util.List;

public class HeapTree {
  private static final int DEFAULT_SIZE = 101; // since we are only computing 100 numbers
  private int[] array = new int[DEFAULT_SIZE];
  private int lastIndex;
  // observer to track swaps
  private int observer;

  /** Other Way */
  public HeapTree() {
    // nothing
  }

  /** Smart Way */
  public HeapTree(int[] array) {
    if (array.length > DEFAULT_SIZE - 1) {
      throw new RuntimeException("Array to big");
    }
    // Copy the array
    System.arraycopy(array, 0, this.array, 1, array.length);
    lastIndex = array.length;
    for (int i = lastIndex / 2; i > 0 ; i--) {
      rebuildHeap(i);
    }
  }

  /** Add an element to the heap */
  public void add(int x) {
    if (lastIndex == array.length - 1) { // resize the heap
      int[] tmp = new int[array.length * 2];
      System.arraycopy(array, 0, tmp, 0, array.length);
      array = tmp;
    }
    lastIndex++;
    array[lastIndex] = x;
    int cursor = lastIndex;
    while (cursor > 1) {
      int parentIndex = cursor / 2;
      if (array[parentIndex] < array[cursor]) {
        swap(array, parentIndex, cursor);
      }
      cursor /= 2;
    }
  }

  /** internal method to rebuild the heap */
  private void rebuildHeap(int node) {
    int cursor = node;
    while (cursor < lastIndex) {
      int left = cursor * 2;
      int right = left + 1;
      int larger = (right <= lastIndex && array[right] > array[left]) ? right : left;
      if (larger <= lastIndex && array[larger] > array[cursor]) {
        swap(array, larger, cursor);
        cursor = larger;
        continue;
      }
      break;
    }
  }

  /** internal method to swap and observe the swap operation */
  private void swap(int[] array, int i, int j) {
    observer++; // track swaps
    int tmp = array[i];
    array[i] = array[j];
    array[j] = tmp;
  }

  /** Tract the number of swaps since the life span of this object */
  public int numberOfSwaps() {
    return observer;
  }

  /** Just remove the first one and rebuild the heap */
  public void remove() {
    if (lastIndex == 0) {
      throw new RuntimeException("opps");
    }
    array[1] = array[lastIndex];
    lastIndex--;
    rebuildHeap(1);
  }

  /** Get the array as a list for the method in the other class */
  public List<Integer> data() {
    List<Integer> tmp = new ArrayList<>();
    for (int i = 1; i < array.length; i++) {
      tmp.add(array[i]);
    }
    return tmp;
  }
}
