package structures;

import java.util.Iterator;

public class ScapegoatTree<T extends Comparable<T>> extends BinarySearchTree<T> {
  private int upperBound;

  @Override
  public void add(T t) {
    BSTNode<T> node = new BSTNode<T>(t, null, null);
    root = addToSubtree(root, node);
    upperBound++;
    if (height() > Math.log(upperBound) / Math.log(3.0/2.0)) {
      node = node.getParent();
      while (3 * subtreeSize(node) <= 2 * subtreeSize(node.getParent())) {
        node = node.getParent();
      }
      node = node.getParent(); //get the scapegoat
      BSTNode<T> parent = node.getParent(); 
      BinarySearchTree<T> tempTree = new BinarySearchTree<T>();
      Iterator<T> iter = inorderIterator(node);
      while (iter.hasNext()) tempTree.add(iter.next());
      tempTree.balance();
      BSTNode<T> tempRoot = tempTree.getRoot();
      if (parent.getRight() == node) {parent.setRight(tempRoot);} 
      else {parent.setLeft(tempRoot);}
    }
  }

  @Override
  public boolean remove(T element) {
    if (super.remove(element)) {
      if (size() * 2 < upperBound) {
        balance();
        upperBound = size();
      }
      return true;
    }
    return false;
  }

}
