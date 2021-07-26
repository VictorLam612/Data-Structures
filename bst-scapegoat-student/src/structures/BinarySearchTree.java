package structures;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

public class BinarySearchTree<T extends Comparable<T>> implements BSTInterface<T> {
  protected BSTNode<T> root;

  public boolean isEmpty() {
    return root == null;
  }

  public int size() {
    return subtreeSize(root);
  }

  protected int subtreeSize(BSTNode<T> node) {
    if (node == null) {
      return 0;
    } else {
      return 1 + subtreeSize(node.getLeft()) + subtreeSize(node.getRight());
    }
  }

  public boolean contains(T t) {
    if (t == null) throw new NullPointerException();
    return getFromTree(t, root) != null;
  }

  private T getFromTree(T t, BSTNode<T> node) {
    if (node == null) return null;
    int j = t.compareTo(node.getData());
    if (j < 0) return getFromTree(t, node.getLeft());
    else if (j == 0) return node.getData(); 
    return getFromTree(t, node.getRight());
  }

  /**
   * remove the data from the tree.
   */
  public boolean remove(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    boolean result = contains(t);
    if (result) {
      root = removeFromSubtree(root, t);
    }
    return result;
  }

  private BSTNode<T> removeFromSubtree(BSTNode<T> node, T t) {
    // node must not be null
    int result = t.compareTo(node.getData());
    if (result < 0) {
      node.setLeft(removeFromSubtree(node.getLeft(), t));
      return node;
    } else if (result > 0) {
      node.setRight(removeFromSubtree(node.getRight(), t));
      return node;
    } else { // result == 0
      if (node.getLeft() == null) {
        return node.getRight();
      } else if (node.getRight() == null) {
        return node.getLeft();
      } else { // neither child is null
        T predecessorValue = getHighestValue(node.getLeft());
        node.setLeft(removeRightmost(node.getLeft()));
        node.setData(predecessorValue);
        return node;
      }
    }
  }

  private T getHighestValue(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getData();
    } else {
      return getHighestValue(node.getRight());
    }
  }

  private BSTNode<T> removeRightmost(BSTNode<T> node) {
    // node must not be null
    if (node.getRight() == null) {
      return node.getLeft();
    } else {
      node.setRight(removeRightmost(node.getRight()));
      return node;
    }
  }

  public T get(T t) {
    if (t == null) throw new NullPointerException();
    return getFromTree(t, root);
  }

  /**
   * add data into the tree.
   */
  public void add(T t) {
    if (t == null) {
      throw new NullPointerException();
    }
    root = addToSubtree(root, new BSTNode<T>(t, null, null));
  }

  protected BSTNode<T> addToSubtree(BSTNode<T> node, BSTNode<T> toAdd) {
    if (node == null) {
      return toAdd;
    }
    int result = toAdd.getData().compareTo(node.getData());
    if (result <= 0) {
      node.setLeft(addToSubtree(node.getLeft(), toAdd));
    } else {
      node.setRight(addToSubtree(node.getRight(), toAdd));
    }
    return node;
  }

  @Override
  public T getMinimum() {
    if (isEmpty()) return null;
    return getMin(root);
  }

  private T getMin(BSTNode<T> node) {
    if (node.getLeft() == null) return node.getData();
    return getMin(node.getLeft());

  }

  @Override
  public T getMaximum() {
    if (isEmpty()) return null;
    return getMax(root);
  }

  private T getMax(BSTNode<T> node) {
    if (node.getRight() == null) return node.getData();
    return getMax(node.getRight());
  }

  @Override
  public int height() {
    if (isEmpty()) return -1;
    return getHeight(root);
  }

  private int getHeight(BSTNode<T> node) {
    if (node == null) return -1;
    return 1 + Math.max(getHeight(node.getLeft()), getHeight(node.getRight()));
  }

  public Iterator<T> preorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    preOrder(queue, root);
    return queue.iterator();
  }

  private void preOrder(Queue<T> queue, BSTNode<T> node) {
    if (node == null) return;
    queue.add(node.getData());
    preOrder(queue, node.getLeft());
    preOrder(queue, node.getRight());
  }

  /**
   * in-order traversal.
   */
  public Iterator<T> inorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, root);
    return queue.iterator();
  }

  public Iterator<T> inorderIterator(BSTNode<T> node) {
    Queue<T> queue = new LinkedList<T>();
    inorderTraverse(queue, node);
    return queue.iterator();
  }
  
  private void inorderTraverse(Queue<T> queue, BSTNode<T> node) {
    if (node != null) {
      inorderTraverse(queue, node.getLeft());
      queue.add(node.getData());
      inorderTraverse(queue, node.getRight());
    }
  }

  public Iterator<T> postorderIterator() {
    Queue<T> queue = new LinkedList<T>();
    postOrder(queue, root);
    return queue.iterator();
  }

  private void postOrder(Queue<T> queue, BSTNode<T> node) {
    if (node == null) return;
    postOrder(queue, node.getLeft());
    postOrder(queue, node.getRight());
    queue.add(node.getData());
  }

  @Override
  public boolean equals(BSTInterface<T> other) {
    return isEqual(root, other.getRoot());
  }

  private boolean isEqual(BSTNode<T> curr, BSTNode<T> that) {
    if (curr == null && that == null) return true;
    if (curr == null || that == null) return false;
    if (curr.getData().equals(that.getData())) 
      return isEqual(curr.getLeft(), that.getLeft()) && isEqual(curr.getRight(), that.getRight());
    return false;
  }

  @Override
  public boolean sameValues(BSTInterface<T> other) {
    if (other == null) throw new NullPointerException();
    Iterator<T> curr = this.inorderIterator();
    Iterator<T> that = other.inorderIterator();
    if (curr.hasNext() && !that.hasNext() || !curr.hasNext() && that.hasNext()) return false;
    while (curr.hasNext() && that.hasNext())
      if (!curr.next().equals(that.next())) 
        return false;
    return true;
  }

  @Override
  public boolean isBalanced() {
    return size() >= Math.pow(2, height()) && size() <= Math.pow(2, height() + 1);
  }

  @Override
  @SuppressWarnings("unchecked")
  public void balance() {
    T[] values = (T[]) new Comparable[size()];
    Iterator<T> iter = inorderIterator();
    int i = 0;
    while (iter.hasNext()) {
      values[i++] = iter.next();
    }
    root = sortedArray2BST(values, 0, values.length - 1);
  }
  
  public BSTNode<T> sortedArray2BST(T[] array, int lower, int upper) { 
    if (lower > upper) return null;
    int mid = (lower + upper) / 2;
    BSTNode<T> node = new BSTNode<T>(array[mid], null, null);
    this.add(array[mid]);
    node.setLeft(sortedArray2BST(array, lower, mid - 1));
    node.setRight(sortedArray2BST(array, mid + 1, upper));
    return node;
  }


  @Override
  public BSTNode<T> getRoot() {
    // DO NOT MODIFY
    return root;
  }

  /**
   * toDotFormat.
   * @param root root of tree.
   * @return type T.
   */
  public static <T extends Comparable<T>> String toDotFormat(BSTNode<T> root) {
    // header
    int count = 0;
    String dot = "digraph G { \n";
    dot += "graph [ordering=\"out\"]; \n";
    // iterative traversal
    Queue<BSTNode<T>> queue = new LinkedList<BSTNode<T>>();
    queue.add(root);
    BSTNode<T> cursor;
    while (!queue.isEmpty()) {
      cursor = queue.remove();
      if (cursor.getLeft() != null) {
        // add edge from cursor to left child
        dot += cursor.getData().toString() + " -> "
            + cursor.getLeft().getData().toString() + ";\n";
        queue.add(cursor.getLeft());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }
      if (cursor.getRight() != null) {
        // add edge from cursor to right child
        dot += cursor.getData().toString() + " -> "
            + cursor.getRight().getData().toString() + ";\n";
        queue.add(cursor.getRight());
      } else {
        // add dummy node
        dot += "node" + count + " [shape=point];\n";
        dot += cursor.getData().toString() + " -> " + "node" + count
            + ";\n";
        count++;
      }

    }
    dot += "};";
    return dot;
  }

  /**
   * main method.
   * @param args arguments.
   */
  public static void main(String[] args) {
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      BSTInterface<String> tree = new BinarySearchTree<String>();
      for (String s : new String[] { "d", "b", "a", "c", "f", "e", "g" }) {
        tree.add(s);
      }
      Iterator<String> iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.preorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
      iterator = tree.postorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();

      System.out.println(tree.remove(r));

      iterator = tree.inorderIterator();
      while (iterator.hasNext()) {
        System.out.print(iterator.next());
      }
      System.out.println();
    }

    BSTInterface<String> tree = new BinarySearchTree<String>();
    for (String r : new String[] { "a", "b", "c", "d", "e", "f", "g" }) {
      tree.add(r);
    }
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
    tree.balance();
    System.out.println(tree.size());
    System.out.println(tree.height());
    System.out.println(tree.isBalanced());
  }

}