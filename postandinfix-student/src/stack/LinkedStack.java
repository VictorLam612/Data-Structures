package stack;

/**
 * A {@link LinkedStack} is a generic stack that is implemented using
 * a Linked List structure to allow for unbounded size.
 */
public class LinkedStack<T> {

  LLNode<T> top;
  int count;

  /**
   * Remove and return the top element on this stack.
   * If stack is empty, return null (instead of throw exception)
   */
  public T pop() {
    if (size() < 1) {
      return null;
    }
    T temp = top.info;
    top = top.link;
    count--;
    return temp;
  }

  /**
   * Return the top element of this stack (do not remove the top element).
   * If stack is empty, return null (instead of throw exception)
   */
  public T top() {
    if (isEmpty()) {
      return null;
    }
    return top.info;
  }

  /**
   * Return true if the stack is empty and false otherwise.
   */
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Return the number of elements in this stack.
   */
  public int size() {
    return count;
  }

  /**
   * Pushes a new element to the top of this stack.
   */
  public void push(T elem) {
    LLNode<T> temp = new LLNode<T>(elem);
    temp.link = top;
    top = temp;
    count++;
  }

}