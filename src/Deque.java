
/******************************************************************************
 *  Compilation:  javac Deque.java
 *  Execution:    java Deque
 *  Dependencies: Iterator
 *
 *
 ******************************************************************************/

import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

  /**
   * construct an empty deque
   * 
   * @return
   */
  public Deque() {

  }

  /**
   * is the deque empty?
   * 
   * @return
   */
  public boolean isEmpty() {
    return true;
  }

  /**
   * return the number of items on the deque
   * 
   * @return the number of items on the deque
   */
  public int size() {
    return -1;
  }

  /**
   * add the item to the front
   * 
   * @param item
   */
  public void addFirst(Item item) {

  }

  /**
   * add the item to the end
   * 
   * @param item
   */
  public void addLast(Item item) {

  }

  /**
   * remove and return the item from the front
   * 
   * @return the item from the front
   */
  public Item removeFirst() {

  }

  /**
   * remove and return the item from the end
   * 
   * @return the item from the end
   */
  public Item removeLast() {

  }

  /**
   * return an iterator over items in order from front to end
   * 
   * @return an iterator over items in order from front to end
   */
  public Iterator<Item> iterator() {

  }

  /**
   * unit testing (optional)
   * 
   * @param args
   */
  public static void main(String[] args) {

  }
}