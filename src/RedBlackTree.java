// --== CS400 Fall 2023 File Header Information ==--
// Name: Muhammad Irfan Bin Mohd Ropi
// Email: binmohdropi@wisc.edu
// Group: A02
// TA: Grant Waldow
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Red-Black Tree implementation that extends BinarySearchTree.
 * This class is generic and accepts a type parameter with the same restrictions as BinarySearchTree,
 * passing it on to the BinarySearchTree class it extends.
 */
public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

  /**
   * Extended node class for Red-Black Tree.
   * Stores the color for each node in addition to the node's parent, children, and data.
   */
  protected static class RBTNode<T> extends Node<T> {
    public int blackHeight = 0;

    public RBTNode(T data) {
      super(data);
    }

    public RBTNode<T> getUp() {
      return (RBTNode<T>) this.up;
    }

    public RBTNode<T> getDownLeft() {
      return (RBTNode<T>) this.down[0];
    }

    public RBTNode<T> getDownRight() {
      return (RBTNode<T>) this.down[1];
    }
  }

  /**
   * Enforces Red-Black Tree properties after inserting a new red node.
   * Resolves any red property violations introduced by the insertion.
   * @param newNode a reference to the newly added red node
   */
  protected void enforceRBTreePropertiesAfterInsert(RBTNode<T> newNode) {
    // Ensure that the root has a black height of 1
    if (newNode == this.root) {
      newNode.blackHeight = 1; // Set the root to black
      return;
    }

    RBTNode<T> parent = newNode.getUp(); // Parent of the new node
    if (parent == null || parent.blackHeight == 1) {
      return;
    }
    RBTNode<T> grandparent = parent.getUp(); // Grandparent of the new node
    if (grandparent == null) {
      return;
    }
    RBTNode<T> uncle; // Uncle of the new node
    if (grandparent.getDownLeft() == parent) {
      uncle = grandparent.getDownRight();
    } else {
      uncle = grandparent.getDownLeft();
    }

    // Case 1: The parent and uncle of the new node are both red
    if (uncle != null && uncle.blackHeight == 0) {
      parent.blackHeight = 1; // Set the parent to black
      uncle.blackHeight = 1; // Set the uncle to black
      grandparent.blackHeight = 0; // Set the grandparent to red
      enforceRBTreePropertiesAfterInsert(grandparent); // Recursively enforce the properties for the grandparent
      return;
    }

    // Left side
    if (grandparent.getDownLeft() == parent) {
      if (parent.getDownLeft() == newNode) {
        // Case 2: The parent is red and the uncle is black, and the new node is the left child of the parent
        if (parent.getUp() == grandparent) {
          this.rotate(parent, grandparent); // Rotate the parent and grandparent
          parent.blackHeight = 1; // Set the parent to black
          grandparent.blackHeight = 0; // Set the grandparent to red
        }
      } else if (parent.getDownRight() == newNode) {
        // Left-right case
        if (newNode.getUp() == parent && parent.getUp() == grandparent) {
          this.rotate(newNode, parent); // Rotate the new node and its parent
          this.rotate(newNode, grandparent); // Rotate the new node and its grandparent
          newNode.blackHeight = 1; // Set the new node to black
          grandparent.blackHeight = 0; // Set the grandparent to red
        }
      }
    }
    // Right side
    else if (grandparent.getDownRight() == parent){
      if (parent.getDownRight() == newNode){
        // Right-right case
        if (parent.getUp() == grandparent) {
          this.rotate(parent, grandparent); // Rotate the parent and grandparent
          parent.blackHeight = 1; // Set the parent to black
          grandparent.blackHeight = 0; // Set the grandparent to red
        }
      }
      // Left side
      else if (parent.getDownLeft() == newNode) {
        // Right-left case
        if (newNode.getUp() == parent && parent.getUp() == grandparent) {
          this.rotate(newNode, parent); // Rotate the new node and its parent
          this.rotate(newNode, grandparent); // Rotate the new node and its grandparent
          newNode.blackHeight = 1; // Set the new node to black
          grandparent.blackHeight = 0; // Set the grandparent to red
        }
      }
    }
  }

  /**
   * Overrides the insert method of BinarySearchTree for Red-Black Tree.
   * Instantiates a new RBTNode<T> with the key passed in and inserts it into the tree.
   * After the node is successfully inserted, calls enforceRBTreePropertiesAfterInsert for this new node.
   * After the call to enforceRBTreePropertiesAfterInsert, sets the root node of the tree to black.
   * @param data to be added into this red-black tree
   * @return true if the value was inserted, false if it was in the tree already
   * @throws NullPointerException when the provided data argument is null
   */
  @Override
  public boolean insert(T data) throws NullPointerException {
    if (data == null)
      throw new NullPointerException("Cannot insert data value null into the tree.");

    RBTNode<T> newNode = new RBTNode<>(data); // Create a new node with the data
    boolean inserted = super.insertHelper(newNode); // Insert the new node into the tree

    // Enforce Red-Black Tree properties after insertion
    if (inserted) {
      enforceRBTreePropertiesAfterInsert(newNode);
      // Set the root node to black after enforcing Red-Black Tree properties
      if (root instanceof RBTNode) {
        ((RBTNode<T>) root).blackHeight = 1; // Set the root node to black
      }
    }

    return inserted;
  }


  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the right side of parent, with black uncle on the left side of parent.
   * The result should be a rotation and swap of colors between parent and grandparent.
   */
  @Test
  public void checkLeftBlackUncleRightSide() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;
    //set the black height of the left child to 1
    ((RBTNode<Integer>)leftChild).blackHeight = 1;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(20);
    String toLevelOrder = tree.toLevelOrderString();
    //check the structure of the tree
    Assertions.assertEquals("[ 15, 10, 20, 7 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);

  }

  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the left side of parent, with black uncle on the right side of parent.
   * The result should be a rotation and swap of colors between parent and grandparent.
   */
  @Test
  public void checkRightBlackUncleLeftSide() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;
    //set the black height of the right child to 1
    ((RBTNode<Integer>)rightChild).blackHeight = 1;
    rightChild.blackHeight = 1;

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(2);
    String toLevelOrder = tree.toLevelOrderString();
    //check the structure of the tree
    Assertions.assertEquals("[ 7, 2, 10, 15 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);

  }

  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the right side of parent, with red uncle on the left side of parent.
   * The result should be a swap of colors between parent and grandparent.
   */
  @Test
  public void checkLeftRedUncleRightSideChild() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(20);
    String toLevelOrder = tree.toLevelOrderString();
    //check the structure of the tree
    Assertions.assertEquals("[ 10, 7, 15, 20 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(1, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(1, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);

  }

  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the left side of parent, with red uncle on the right side of parent.
   * The result should be a swap of colors between parent and grandparent.
   */
  @Test
  public void checkRightRedUncleLeftSideChild() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(5);
    String toLevelOrder = tree.toLevelOrderString();
    //check the structure of the tree
    Assertions.assertEquals("[ 10, 7, 15, 5 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(1, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(1, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);


  }

  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the right side of parent, with black uncle on the right side of parent.
   * The result should be a rotation and swap of colors between newnode and parent,
   * then newnode and grandparent.
   */
  @Test
  public void checkRightBlackUncleRightSideChild() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;
    //set the black height of the right child to 1
    ((RBTNode<Integer>)rightChild).blackHeight = 1;

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(9);
    String toLevelOrder = tree.toLevelOrderString();

    //check the structure of the tree
    Assertions.assertEquals("[ 9, 7, 10, 15 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);


  }

  /**
   * This test checks the structure of the tree after inserting a node. This test covers the case
   * where the new node is on the left side of parent, with black uncle on the left side of parent.
   * The result should be a rotation and swap of colors between newnode and parent,
   * then newnode and grandparent.
   */
  @Test
  public void checkLeftBlackUncleLeftSideChild() {
    //create a root node
    RBTNode<Integer> root = new RBTNode<>(10);
    root.blackHeight = 1;

    //create a left child node
    RBTNode<Integer> leftChild = new RBTNode<>(7);
    leftChild.up = root;
    root.down[0] = leftChild;
    //set the black height of the left child to 1
    ((RBTNode<Integer>)leftChild).blackHeight = 1;

    //create a right child node
    RBTNode<Integer> rightChild = new RBTNode<>(15);
    rightChild.up = root;
    root.down[1] = rightChild;
    //set the black height of the right child to 1

    //create a red black tree
    RedBlackTree<Integer> tree = new RedBlackTree<>();
    tree.root = root;
    tree.size = 3;

    //insert a new node
    tree.insert(12);
    String toLevelOrder = tree.toLevelOrderString();

    //check the structure of the tree
    Assertions.assertEquals("[ 12, 10, 15, 7 ]", toLevelOrder);
    Assertions.assertEquals(1, ((RBTNode<Integer>) tree.root).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownLeft()).blackHeight);
    Assertions.assertEquals(0, (((RBTNode<Integer>) tree.root).getDownRight()).blackHeight);
  }



}
