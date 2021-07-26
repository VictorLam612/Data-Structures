package structures;


import java.util.Iterator;

public class RecursiveList<T> implements ListInterface<T> {
  @SuppressWarnings("hiding")
  class LLNode<T> {
    public T data;
    public LLNode<T> link;
    
    LLNode(T data, LLNode<T> link) { 
      this.data = data; 
      this.link = link; 
    }
    
    public void setData(T data) {
      this.data = data;
    }
    
    public T getData() {
      return this.data;
    }
    
    public void setLink(LLNode<T> link) {
      this.link = link; 
    }
    
    public LLNode<T> getLink() { 
      return this.link; 
    }
  }
  
  @SuppressWarnings("hiding")
  class LinkedNodeIterator implements Iterator<T> {
    LLNode<T> curr;
    
    public LinkedNodeIterator() {
      curr = head;
    }
    
    @Override
    public boolean hasNext() {
      return curr != null;
    }

    @Override
    public T next() {
      T temp = curr.getData();
      curr = curr.getLink();
      return temp;
    }
    
  }
  
  LLNode<T> head;
  LLNode<T> tail;
  int size;
  
  //Constructor
  public RecursiveList() {
    head = null;
    tail = null;
    size = 0;
  }
  
  
  @Override
  public Iterator<T> iterator() {
    return new LinkedNodeIterator();
  }

  
  @Override
  public int size() {
    return size;
  }

  
 
  @Override
  public ListInterface<T> insertFirst(T elem) {
    if (elem == null) {
      throw new NullPointerException();
    }
    LLNode<T> temp = new LLNode<T>(elem, head);
    if (isEmpty()) {
      tail = temp;
    }
    head = temp;
    size++;
    return this;
  } 

  
 
  @Override
  public ListInterface<T> insertLast(T elem) {
    if (elem == null) {
      throw new NullPointerException();
    }
    LLNode<T> temp = new LLNode<T>(elem, null);
    if (isEmpty()) { //if empty
      head = temp; //head is also equal
    } else { //if not empty - there's a tail
      tail.setLink(temp); //tail points to new elem
    }
    tail = temp;
    size++;
    return this;
  }

 
  @Override
  public ListInterface<T> insertAt(int index, T elem) {
    if (index > size || index < 0) {
      throw new IndexOutOfBoundsException();
    } else if (elem == null) {
      throw new NullPointerException();
    }
    if (index == 0) {
      return insertFirst(elem);
    } else if (index == size) {
      return insertLast(elem);
    } else {
      LLNode<T> curr = findNode(index - 1, 0, head);
      LLNode<T> temp = new LLNode<T>(elem, curr.getLink());
      curr.setLink(temp);
      size++;
      return this;
    }
  }


  @Override
  public T removeFirst() {
    if (size == 0) {
      throw new IllegalStateException();
    }
    T temp = head.getData();
    if (size == 1) {
      tail = null;
    }
    head = head.getLink();
    size--;
    return temp;
  }


  @Override
  public T removeLast() {
    if (size == 0) {
      throw new IllegalStateException();
    }
    T temp = tail.getData();
    if (size == 1) {
      head = null;
      tail = null;
    } else {
      LLNode<T> last = findNode(size - 2, 0, head); //Size - 1 = tail
      tail = last;
      tail.setLink(null);
    }
    size--;
    return temp;
  }

 
  @Override
  public T removeAt(int index) {
    if (index >= size || index < 0) {
      throw new IndexOutOfBoundsException();
    } else if (index == 0) {
      return removeFirst();
    } else if (index == size - 1) {
      return removeLast();
    } else {
      LLNode<T> last = findNode(index - 1, 0, head); //gets node before target
      T temp = last.getLink().getData(); //gets data from next node
      last.setLink(last.getLink().getLink()); //set link to next next node
      size--;
      return temp;
    }
  }


  @Override
  public T getFirst() {
    if (size == 0) {
      throw new IllegalStateException();
    }
    return head.getData();
  }

 
  @Override
  public T getLast() {
    if (size == 0) {
      throw new IllegalStateException();
    }
    return tail.getData();
  }


  @Override
  public T get(int index) {
    if (index >= size || index < 0) {
      throw new IndexOutOfBoundsException();
    }
    LLNode<T> temp = findNode(index, 0, head);
    return temp.getData();
  }

  
  @Override
  public boolean remove(T elem) {
    if (elem == null) {
      throw new NullPointerException();
    }
    int c = indexOf(elem);
    if (c == -1) { //not found
      return false;
    }
    removeAt(c);
    return true;
  }
 
  @Override
  public int indexOf(T elem) {
    if (elem == null) {
      throw new NullPointerException();
    }
    return findIndex(elem, 0, head);
  }

  @Override
  public boolean isEmpty() {
    return head == null;
  }

  private LLNode<T> findNode(int index, int count, LLNode<T> curr) {
    if (index == count) {
      return curr;
    }
    return findNode(index, ++count, curr.getLink());
  }

  private LLNode<T> getPrev(T elem, LLNode<T> curr) {
    if (curr == null) {
      return null;
    }
    if (curr.getLink().getData().equals(elem)) {
      return curr;
    }
    return getPrev(elem, curr.getLink());
  }

  private int findIndex(T elem, int count, LLNode<T> curr) {
    if (curr == null) {
      return -1;
    }
    if (curr.getData().equals(elem)) {
      return count;
    }
    return findIndex(elem, ++count, curr.getLink());
  }
}
