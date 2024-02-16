// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * This class is the red black tree implementation of the IterableMultiKeySortedCollectionInterface.
 * It uses a red black tree to store the keyLists, which are lists of keys that have the same value.
 * @param <T>
 */
public class IterableMultiKeyRBT<T extends Comparable<T>> extends RedBlackTree<KeyListInterface<T>>
    implements IterableMultiKeySortedCollectionInterface<T> {

  // instance variables
  Comparable<T> startingPoint;
  int numKeys;

  /**
   * Inserts value into tree that can store multiple objects per key by keeping lists of objects in
   * each node of the tree.
   *
   * @param key object to insert
   * @return true if a new node was inserted, false if the key was added into an existing node
   */
  @Override
  public boolean insertSingleKey(T key) {
    // create a new keylist
    KeyList<T> keyList = new KeyList<>(key);
    boolean newNodeInserted = false;
    // find node with the same key
    if (findNode(keyList) != null) { // the node exist, add the key to existing keyList
      findNode(keyList).data.addKey(key);
    } else if (findNode(keyList) == null) { // the node does not exist, insert a new node
      //creating a new rbt node
      insert(keyList);
      newNodeInserted = true;
    }
    numKeys++;
    return newNodeInserted;
  }

  /**
   * @return the number of values in the tree.
   */
  @Override
  public int numKeys() {
    return this.numKeys;
  }

  /**
   * Returns an iterator that does an in-order iteration over the tree.
   */
  @Override
  public Iterator<T> iterator() {
    Iterator<T> keyIterator = new Iterator<T>() {

      // initialize the stack with the initial stack.
      Stack<Node<KeyListInterface<T>>> stack = getStartStack();

      // initialize the keyListIterator with the first keyList in the stack.
      Iterator<T> keyListIterator = !stack.isEmpty() ? stack.peek().data.iterator() : null;

      /**
       * Checks if there is a next element in the tree.
       * @return true if there is a next element, false if not
       */
      @Override
      public boolean hasNext() {
        // if the keyListIterator is null or the keyListIterator does not have a next element and
        // the stack is empty, return false.
        if (keyListIterator == null || (!keyListIterator.hasNext() && stack.isEmpty())) {
          return false;
        }

        // if the keyListIterator does not have a next element, pop the stack and set the
        // keyListIterator to the next keyList.
        while (!keyListIterator.hasNext()) {
          // if the current node has a right child, push the right child and all of its left
          if (stack.peek().down[1] != null) {
            Node<KeyListInterface<T>> current = stack.pop().down[1];
            while (current != null) {
              stack.push(current);
              current = current.down[0];
            }
            // if the stack is the last keyList, and there is no next element, return false.
          } else if (stack.size() <= 1) {
            stack.pop();
            return false;
            // if the stack is not the last keyList, pop the stack.
          } else {
            stack.pop();
          }
          // set the keyListIterator to the next keyList.
          keyListIterator = stack.peek().data.iterator();
        }

        return true;
      }

      /**
       * Returns the next element in the tree.
       * @return T key - the next element in the tree
       */
      @Override
      public T next() {
        // if there is no next element, throw a NoSuchElementException.
        if (!hasNext()) {
          throw new NoSuchElementException("No more elements in the tree.");
        }
        // return the next element in the tree.
        return keyListIterator.next();
      }
    };

    return keyIterator;
  }


  /**
   * Return an initial stack that contains all the keyLists that are greater than or equal to the
   * startpoint (along the search path) if the startpoint is not null. If the startpoint is null,
   * return a stack that contains all the keyLists from the root to the smallest keyListin the tree.
   * @return
   */
  protected Stack<Node<KeyListInterface<T>>> getStartStack() {

    // initialize the stack and tempstack.
    Stack<Node<KeyListInterface<T>>> stack = new Stack<>();
    Stack<Node<KeyListInterface<T>>> tempstack = new Stack<>();
    Node<KeyListInterface<T>> current = root;

    // if the root is null, return the stack.
    if (root == null) {
      return stack;
    }

    // if the startingPoint is null, push all keylist from the root to the smallest keylist into stack.
    if (startingPoint == null) {
      while (current != null) {
        stack.push(current);
        current = current.down[0];
      }
      return stack;
      // if the startingPoint is not null, push keylist that is greater than or equal to the
      // startingPoint into stack along the search path.
    } else {
      while (current != null || tempstack.size() > 0) {
        while (current != null) {
          tempstack.push(current);
          // if the startingPoint is less than the current keyList, push the current keyList into stack
          if (startingPoint.compareTo(current.data.iterator().next()) < 0) {
            stack.push(current);
            // if the startingPoint is equal to the current keyList, push the current keyList into stack
          } else if (startingPoint.compareTo(current.data.iterator().next()) == 0) {
            stack.push(current);
            return stack;
          }
          // move to the next keyList
          current = current.down[0];
        }
        // pop the tempstack
        current = tempstack.pop();

        // if the startingPoint is less than the current keyList, return the stack.
        if (startingPoint.compareTo(current.data.iterator().next()) < 0) {
          return stack;
        }
        current = current.down[1];
      }
    }

    return stack;
  }

  /**
   * Sets the starting point for iterations. Future iterations will start at the starting point or
   * the key closest to it in the tree. This setting is remembered until it is reset. Passing in
   * null disables the starting point.
   *
   * @param startPoint the start point to set for iterations
   */
  @Override
  public void setIterationStartPoint(Comparable<T> startPoint) {
    this.startingPoint = startPoint;
  }

  /**
   * Resets the red black tree to an empty state.
   */
  @Override
  public void clear() {
    super.clear();
    this.numKeys = 0;
  }

  /**
   * Test if the numKeys method of the IterableMultiKeyRBT correctly reports the number of unique
   * keys present in the tree.
   */
  @Test
  public void testNumKeys() {
    // Initialize an empty Red-Black Tree (RBT) for Integers
    IterableMultiKeyRBT<Integer> rbt = new IterableMultiKeyRBT<>();

    // Insert four keys into the RBT: 1, 2, 3, and another 3.
    // The last 3 should not be counted as a new key since it is a duplicate.
    rbt.insertSingleKey(1);
    rbt.insertSingleKey(2);
    rbt.insertSingleKey(3);
    rbt.insertSingleKey(3);

    // Assert that the total number of unique keys is 3 (1, 2, and 3).
    Assertions.assertEquals(4, rbt.numKeys());
  }

  /**
   * Test if the iterator of the IterableMultiKeyRBT correctly iterates over all the keys in the
   * RBT from the startingPoint that is non-existent.
   */
  @Test
  public void testNonExistentStartingPoint() {
    // Initialize an empty Red-Black Tree (RBT) for Integers and get its iterator
    IterableMultiKeyRBT<String> rbt = new IterableMultiKeyRBT<>();


    // Insert 10 keys into the RBT
    rbt.insertSingleKey("P");
    rbt.insertSingleKey("L");
    rbt.insertSingleKey("U");
    rbt.insertSingleKey("F");
    rbt.insertSingleKey("O");
    rbt.insertSingleKey("R");
    rbt.insertSingleKey("Z");
    rbt.insertSingleKey("D");
    rbt.insertSingleKey("G");
    rbt.insertSingleKey("X");


    rbt.setIterationStartPoint("Q");


    String last = null;

    // Traverse the tree using a for-each loop. This will use the iterator of the RBT
    for (String key : rbt) {
      // If there's a previous key (last), ensure the current key (key) is greater or equal.
      // This asserts the tree's iterator provides keys in ascending order.
      if (last != null) {
        Assert.assertTrue(last.compareTo(key) <= 0);
      }
      last = key; // Update the 'last' key for the next iteration
    }
  }

  /**
   * Test if the iterator of the IterableMultiKeyRBT correctly traverses keys in ascending order.
   */
  @Test
  public void testExpectedIteratorOrder() {
    // Initialize an empty Red-Black Tree (RBT) for Strings
    IterableMultiKeyRBT<String> rbt = new IterableMultiKeyRBT<>();
    // Initialize an array of expected keys in ascending order
    String[] expected = {"Ayam", "Bola", "Bola", "Bola", "Cadek", "Cadek","Cadek", "Dadu"};

    // Insert four string keys into the RBT in no particular order
    rbt.insertSingleKey("Ayam");
    rbt.insertSingleKey("Cadek");
    rbt.insertSingleKey("Cadek");
    rbt.insertSingleKey("Cadek");
    rbt.insertSingleKey("Dadu");
    rbt.insertSingleKey("Bola");
    rbt.insertSingleKey("Bola");
    rbt.insertSingleKey("Bola");

    // Traverse the tree using a for-each loop. This will use the iterator of the RBT.
    int count = 0;

    for (String key : rbt) {
      // Assert that the current key is the expected key
      Assertions.assertEquals(expected[count], key);
      count++;
    }
  }

  /**
   * Test if the clear method reset the RedBlackTree to its initial state.
   */
  @Test
  public void testClear(){
    // Initialize an empty Red-Black Tree (RBT) for Strings
    IterableMultiKeyRBT<String> rbt = new IterableMultiKeyRBT<>();

    // Insert four string keys into the RBT in no particular order
    rbt.insertSingleKey("Ayam");
    rbt.insertSingleKey("Cadek");
    rbt.insertSingleKey("Cadek");
    rbt.insertSingleKey("Dollah");

    // Clear the RBT
    rbt.clear();

    // Assert that the RBT is empty
    Assertions.assertEquals(0, rbt.numKeys());
    Assertions.assertEquals(0, rbt.size());
    Assertions.assertNull(rbt.root);
  }
}
