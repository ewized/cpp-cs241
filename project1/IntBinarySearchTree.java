/*
  Joshua Rodriguez
  4/19/2017
  CS241
 */
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class IntBinarySearchTree {
  private Integer root;
  private IntBinarySearchTree left;
  private IntBinarySearchTree right;

  /** Add each element from the array into the tree */
  public IntBinarySearchTree(int[] array) {
    for (int value : array) {
      add(value);
    }
  }

  public IntBinarySearchTree(int root) {
    this.root = Objects.requireNonNull(root, "Root element can not be null");
  }

  /** Get the value of the node */
  public int value() {
    return root;
  }

  /** Get the left node within an optional class */
  public Optional<IntBinarySearchTree> leftNode() {
    return Optional.ofNullable(left);
  }

  /** Get the right node within the optional class */
  public Optional<IntBinarySearchTree> rightNode() {
    return Optional.ofNullable(right);
  }

  /** Check if this tree is a leaf */
  public boolean isLeaf() {
    return left == null && right == null;
  }

  /** Add elements to the tree */
  public void add(int value) {
    if (root == null) { // Set the value of the root, only will occur with the int[] array constructor
      this.root = value;
      return;
    }
    if (value < root) { // Add to the left side of the tree
      if (left == null) {
        left = new IntBinarySearchTree(value);
      } else {
        left.add(value);
      }
    } else if (value == root) {
      throw new IllegalArgumentException(value + " already exists, ignore.");
    } else {
      if (right == null) {
        right = new IntBinarySearchTree(value);
      } else {
        right.add(value);
      }
    }
  }

// First attempt, little buggy
//  /** Remove element from the tree */
//  public IntBinarySearchTree remove(int value) {
//    if (value > value() && right != null){
//      right = right.remove(value);
//    } else if (value < value() && left != null) {
//      left = left.remove(value);
//    } else if (left != null && right != null) {
//      root = right.min().value();
//      right = right.remove(value());
//    } else {
//      return (left == null) ? right : left;
//    }
//    return this;
//  }

  /** Remove elements to the tree */
  public IntBinarySearchTree remove(int value) {
    int beforeSize = this.size();
    IntBinarySearchTree tmp = remove(value, this);
    if (tmp != null && beforeSize == tmp.size()) {
      throw new IllegalArgumentException(value + " doesn't exist!");
    }
    return tmp;
  }

  /** Helper method to handle the recursive removal, while checking if the value is absent */
  private IntBinarySearchTree remove(int value, IntBinarySearchTree tree) {
    if (tree == null) {
      return null;
    }
    if (value > tree.value()){
      tree.right = remove(value, tree.right);
    } else if (value < tree.value()) {
      tree.left = remove(value, tree.left);
    } else if (tree.left != null && tree.right != null) {
      tree.root = tree.right.min().value();
      tree.right = remove(tree.value(), tree.right);
    } else {
      tree = (tree.left == null) ? tree.right : tree.left;
    }
    return tree;
  }

  /** Get the size of the tree */
  public int size() {
    int i = 1;
    if (left != null) {
      i += left.size();
    }
    if (right != null) {
      i += right.size();
    }
    return i;
  }

  /** Get the far left element of the tree its also the left element */
  public IntBinarySearchTree min() {
    return (left == null) ? this : left.min();
  }

  /** Traverse the tree with pre order */
  public void traversePreorder(Consumer<IntBinarySearchTree> consumer) {
    consumer.accept(this);
    if (left != null) {
      left.traversePreorder(consumer);
    }
    if (right != null) {
      right.traversePreorder(consumer);
    }
  }

  /** Traverse the tree with in order */
  public void traverseInorder(Consumer<IntBinarySearchTree> consumer) {
    if (left != null) {
      left.traverseInorder(consumer);
    }
    consumer.accept(this);
    if (right != null) {
      right.traverseInorder(consumer);
    }
  }

  /** Traverse the tree with post order */
  public void traversePostorder(Consumer<IntBinarySearchTree> consumer) {
    if (left != null) {
      left.traversePostorder(consumer);
    }
    if (right != null) {
      right.traversePostorder(consumer);
    }
    consumer.accept(this);
  }
}
