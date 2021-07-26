package structures;

import comparators.IntegerComparator;
import comparators.ReverseIntegerComparator;

import java.util.Comparator;
import java.util.Iterator;

public class MaxQueue<V> implements PriorityQueue<Integer, V> {
  
  StudentArrayHeap<Integer, V> heap;
  IntegerComparator comparator;
  
  public MaxQueue() {
    comparator = new IntegerComparator();
    heap = new StudentArrayHeap<Integer, V>(comparator);
  }
  
  public PriorityQueue<Integer, V> enqueue(Integer priority, V value) {
    heap.add(priority, value);
    return this;
  }
  
  public V dequeue() {
    return heap.remove();
  }
  
  public V peek() {
    return heap.peek();
  }
  
  public Iterator<Entry<Integer, V>> iterator() {
    return heap.asList().iterator();
  }
  
  public Comparator<Integer> getComparator() {
    return heap.getComparator();
  }
  
  public int size() {
    return heap.size();
  }
  
  public boolean isEmpty() {
    return heap.isEmpty();
  }
  
  public static void main(String[] args) {
    MaxQueue<String> queue = new MaxQueue<String>();
    queue.enqueue(100, "Highest priority");
    queue.enqueue(50, "High priority");
    queue.enqueue(25, "Medium priority");
    queue.enqueue(0, "Low priority");
    for (int i = 0; i < queue.size(); i++) {
      System.out.print(queue.dequeue());
    }
  }
}
