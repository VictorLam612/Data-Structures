package sets;

import java.util.Iterator;
import java.util.NoSuchElementException;

class LinkedNodeIterator<E> implements Iterator<E> {
  LinkedNode<E> head;

  // Constructors
  public LinkedNodeIterator(LinkedNode<E> head) {
    this.head = head;
  }

  @Override
  public boolean hasNext() {
    return head != null;
  }

  @Override
  public E next() {
    if (head == null) {
      throw new NoSuchElementException();
    }
    E temp = head.getData();
    head = head.getNext();
    return temp;
  }

  @Override
  public void remove() {
    // Nothing to change for this method
    throw new UnsupportedOperationException();
  }
}
