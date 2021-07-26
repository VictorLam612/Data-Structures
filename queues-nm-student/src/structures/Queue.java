package structures;

import java.util.NoSuchElementException;

/**************************************************************************************
 * NOTE: before starting to code, check support/structures/UnboundedQueueInterface.java
 * for detailed explanation of each interface method, including the parameters, return
 * values, assumptions, and requirements
 ***************************************************************************************/
public class Queue<T> implements UnboundedQueueInterface<T> {
  
  @SuppressWarnings("hiding")
  class Node<T> {
    public T data;
    public Node<T> next;
    
    public Node(T data) { 
      this.data = data;
    }
    
    public Node(T data, Node<T> next) {
      this.data = data; 
      this.next = next;
    }
  }

  int size;
  Node<T> head;
  Node<T> tail;
  
  public Queue() {    
    
  }
  
  /*
   * Copy Constructor. Copies "other" without aliasing
   */
  public Queue(Queue<T> other) {
    Queue<T> temp = new Queue<T>();
    while (!other.isEmpty()) {
      T data = other.dequeue();
      enqueue(data);
      temp.enqueue(data);
    }
    while (!temp.isEmpty()) {
      other.enqueue(temp.dequeue());
    }
  }

  @Override
  public boolean isEmpty() {
    return size == 0;
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  public void enqueue(T element) {
    Node<T> temp = new Node<T>(element);
    if (tail == null) {
      head = temp;
    } else {
      tail.next = temp;
    }
    tail = temp;
    size++;
  }

  @Override
  public T dequeue() throws NoSuchElementException {
    if (isEmpty()) {
      throw new NoSuchElementException("NoSuchElement");
    }
    size--;
    T temp = head.data;
    head = head.next;
    if (head == null) {
      tail = null;
    }
    return temp;
  }

  @Override
  public T peek() throws NoSuchElementException {
    if (isEmpty()) {
      throw new NoSuchElementException("NoSuchElement");
    }
    return head.data;
  }


  @Override
  public UnboundedQueueInterface<T> reversed() {
    Queue<T> that = new Queue<T>();
    reversal(head, that);
    Node<T> temp = head;
    head = tail;
    tail = temp;
    return that;
  }
  
  /*
   * Helper method. Recursion to reverse 
   */
  public void reversal(Node<T> node, Queue<T> that) {
    if (node != null) {
      reversal(node.next, that);
      that.enqueue(node.data);
    }
  }
}


