package sets;

import java.util.Iterator;

/**
 * A LinkedList-based implementation of Set
 */

/********************************************************
 * NOTE: Before you start, check the Set interface in
 * Set.java for detailed description of each method.
 *******************************************************/

/********************************************************
 * NOTE: for this project you must use linked lists
 * implemented by yourself. You are NOT ALLOWED to use
 * Java arrays of any type, or any Collection-based class 
 * such as ArrayList, Vector etc. You will receive a 0
 * if you use any of them.
 *******************************************************/ 

/********************************************************
 * NOTE: you are allowed to add new methods if necessary,
 * but do NOT add new files (as they will be ignored).
 *******************************************************/

public class LinkedSet<E> implements Set<E> {
  private LinkedNode<E> head = null;

  // Constructors
  public LinkedSet() {
  }

  public LinkedSet(E e) {
    this.head = new LinkedNode<E>(e, null);
  }

  private LinkedSet(LinkedNode<E> head) {
    this.head = head;
  }

  @SuppressWarnings("unused")
  @Override
  public int size() {
    int count = 0;
    for (E temp : this) {
      count++;
    }
    return count;
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  @Override
  public Iterator<E> iterator() {
    return new LinkedNodeIterator<E>(this.head);
  }

  @Override
  public boolean contains(Object o) {
    for (E temp : this) {
      if (temp.equals(o)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public boolean isSubset(Set<E> that) {
    for (E curr : this) {
      if (!that.contains(curr)) {
        return false;
      }
    }
    return true;
  }

  @Override
  public boolean isSuperset(Set<E> that) {
    return that.isSubset(this);
  }

  @Override
  public Set<E> adjoin(E e) {
    if (this.contains(e)) {
      return this;
    }
    LinkedNode<E> temp = new LinkedNode<E>(e, head);
    return new LinkedSet<E>(temp);
  }

  @Override
  public Set<E> union(Set<E> that) {
    LinkedNode<E> front = head;
    for (E curr : that) {
      if (!this.contains(curr)) {
        LinkedNode<E> temp = new LinkedNode<E>(curr, front);
        front = temp;
      }
    }
    return new LinkedSet<E>(front);
  }

  @Override
  public Set<E> intersect(Set<E> that) {
    LinkedNode<E> front = null;
    for (E curr : that) {
      if (this.contains(curr)) {
        LinkedNode<E> temp = new LinkedNode<E>(curr, front);
        front = temp;
      }
    }
    return new LinkedSet<E>(front);
  }

  @Override
  public Set<E> subtract(Set<E> that) {
    LinkedNode<E> front = null;
    for (E curr : this) {
      if (!that.contains(curr)) {
        LinkedNode<E> temp = new LinkedNode<E>(curr, front);
        front = temp;
      }
    }
    return new LinkedSet<E>(front);
  }

  @Override
  public Set<E> remove(E e) {
    LinkedNode<E> front = null;
    for (E curr : this) {
      if (curr.equals(e)) {
        continue;
      }
      LinkedNode<E> temp = new LinkedNode<E>(curr, front);
      front = temp;
    }
    return new LinkedSet<E>(front);
  }

  @Override
  @SuppressWarnings("unchecked")
  public boolean equals(Object o) {
    if (! (o instanceof Set)) {
      return false;
    }
    Set<E> that = (Set<E>)o;
    return this.isSubset(that) && that.isSubset(this);
  }

  @Override
  public int hashCode() {
    int result = 0;
    for (E e : this) {
      result += e.hashCode();
    }
    return result;
  }

}
