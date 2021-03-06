package structures;

import comparators.IntegerComparator;
import comparators.ReverseIntegerComparator;

import java.util.Comparator;
import java.util.Iterator;

public class MinQueue<V> implements PriorityQueue<Integer, V> {
  StudentArrayHeap<Integer, V> heap;
  ReverseIntegerComparator comparator;
  
  public MinQueue() {
    comparator = new ReverseIntegerComparator();
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
}

