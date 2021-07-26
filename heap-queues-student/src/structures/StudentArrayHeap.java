package structures;

import java.util.Comparator;
import java.util.Iterator;

import comparators.StringLengthComparator;

public class StudentArrayHeap<P, V> extends AbstractArrayHeap<P, V> {
  
  
  protected StudentArrayHeap(Comparator<P> comparator) {
    super(comparator);
  }

  protected int getLeftChildOf(int i) {
    if (i < 0) throw new IndexOutOfBoundsException();
    return (i * 2) + 1;
  }
  
  protected int getRightChildOf(int i) {
    if (i < 0) throw new IndexOutOfBoundsException();
    return (i * 2) + 2;
  }
  
  protected int getParentOf(int i) {
    if (i < 1) throw new IndexOutOfBoundsException();
    return (i - 1)/2;
  }
  
  protected void bubbleUp(int i) {
    Entry<P, V> temp = heap.get(i);
    int parent = (i - 1)/2;
    while (i > 0 && comparator.compare(temp.getPriority(), heap.get(parent).getPriority()) > 0) {
      heap.set(i, heap.get(parent));
      i = parent;
      parent = (i - 1)/2;
    }
    heap.set(i, temp);
  }
  
  protected void bubbleDown(int i) {
    int largerChild;
    Entry<P, V> elem = heap.get(i);
    while (i < size()/2) {
      int left = getLeftChildOf(i), right = getRightChildOf(i);
      if (right < size() && comparator.compare(heap.get(left).getPriority(), heap.get(right).getPriority()) < 0) {
        largerChild = right;
      } else {
        largerChild = left;
      }
      if (comparator.compare(elem.getPriority(), heap.get(largerChild).getPriority()) >= 0) {
        break;
      }
      heap.set(i, heap.get(largerChild));
      i = largerChild;
    }
    heap.set(i, elem);
  }
}

