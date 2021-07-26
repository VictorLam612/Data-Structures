package filesystem;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import structures.Queue;


/**
 * An iterator to perform a level order traversal of part of a 
 * filesystem. A level-order traversal is equivalent to a breadth-
 * first search.
 */
public class LevelOrderIterator extends FileIterator<File> {

  Queue<File> root;
  File[] list;
  
  /**
   * Instantiate a new LevelOrderIterator, rooted at the rootNode.
   * @param rootNode : node of the root.
   * @throws FileNotFoundException if the rootNode does not exist.
   */
  public LevelOrderIterator(File rootNode) throws FileNotFoundException {
    if (!rootNode.exists()) {
      throw new FileNotFoundException("FileNotFound");
    }
    root = new Queue<File>();
    root.enqueue(rootNode);
 
  }

  @Override
  public boolean hasNext() {
    return !root.isEmpty();
  }

  @Override
  public File next() throws NoSuchElementException {
    File temp = root.dequeue();
    if (temp.isDirectory()) {
      File[] list = temp.listFiles();
      Arrays.sort(list);
      for (int i = 0; i < list.length; i++) {
        root.enqueue(list[i]);
      }
    }
    return temp;
  }

  @Override
  public void remove() {
    // Leave this one alone.
    throw new UnsupportedOperationException();
  }

}
