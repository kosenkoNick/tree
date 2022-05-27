package com.example.tree;

import java.util.Iterator;

public class BST<E extends Comparable<E>> implements Tree<E> {
  protected TreeNode<E> root;
  protected int size = 0;

  @Override
  public boolean isPresent(E e) {
    TreeNode<E> current = root;

    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        current = current.right;
      } else {
        return true; // Element is found
      }
    }
    return false;
  }

  @Override
  public boolean insert(E e) {
    // root is absent?
    if (root == null) {
      root = new TreeNode<>(e); // Create a new root
    } else {
      // find where to insert element
      TreeNode<E> parent = null;
      TreeNode<E> current = root;
      while (current != null) {
        if (e.compareTo(current.element) < 0) {
          parent = current;
          current = current.left;
        } else if (e.compareTo(current.element) > 0) {
          parent = current;
          current = current.right;
        } else {
          return false; // Duplicate node not inserted
        }
      }

      // Create the new node
      if (e.compareTo(parent.element) < 0) {
        parent.left = new TreeNode<>(e);
      } else {
        parent.right = new TreeNode<>(e);
      }
    }

    size++;
    return true; // Element inserted successfully
  }

  @Override
  public void inorder() {
    inorder(root);
  }
  protected void inorder(TreeNode<E> root) {
    if (root == null) {
      return;
    }
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  @Override
  public void preorder() {
    preorder(root);
  }
  protected void preorder(TreeNode<E> root) {
    if (root == null) {
      return;
    }
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }

  @Override
  public void postorder() {
    postorder(root);
  }
  protected void postorder(TreeNode<E> root) {
    if (root == null) {
      return;
    }
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  @Override
  public int size() {
    return size;
  }
  public TreeNode<E> getRoot() {
    return root;
  }

  @Override
  public boolean delete(E e) {
    // find the element we want to delete
    TreeNode<E> parent = null;
    TreeNode<E> current = root;
    while (current != null) {
      if (e.compareTo(current.element) < 0) {
        parent = current;
        current = current.left;
      } else if (e.compareTo(current.element) > 0) {
        parent = current;
        current = current.right;
      } else {
        break; // Element is in the tree pointed at by current
      }
    }

    if (current == null) {
      return false; // Element is not in the tree
    }

    // Case 1: current has no left child
    if (current.left == null) {
      // Connect the parent with the right child of the current node
      if (parent == null) {
        root = current.right;
      } else {
        if (e.compareTo(parent.element) < 0) {
          parent.left = current.right;
        } else {
          parent.right = current.right;
        }
      }
    } else {
      // Case 2: The current node has a left child.
      TreeNode<E> parentOfRightMost = current;
      TreeNode<E> rightMost = current.left;

      while (rightMost.right != null) {
        parentOfRightMost = rightMost;
        rightMost = rightMost.right; // Keep going to the right
      }

      // Replace the element in current by the element in rightMost
      current.element = rightMost.element;

      // Eliminate rightmost node
      if (parentOfRightMost.right == rightMost) {
        parentOfRightMost.right = rightMost.left;
      } else
      // Special case: parentOfRightMost == current
      {
        parentOfRightMost.left = rightMost.left;
      }
    }

    size--;
    return true; // Element deleted successfully
  }

  @Override
  /** Obtain an iterator. Use inorder. */
  public Iterator<E> iterator() {
    return new InorderIterator();
  }

  // Inner class InorderIterator
  private class InorderIterator implements Iterator<E> {
    // Store the elements in a list
    private java.util.ArrayList<E> list = new java.util.ArrayList<>();
    private int current = 0; // Point to the current element in list

    public InorderIterator() {
      inorder(); // Traverse binary tree and store elements in list
    }

    /**
     * Inorder traversal from the root
     */
    private void inorder() {
      inorder(root);
    }

    /**
     * Inorder traversal from a subtree
     */
    private void inorder(TreeNode<E> root) {
      if (root == null) {
        return;
      }
      inorder(root.left);
      list.add(root.element);
      inorder(root.right);
    }

    @Override
    /** More elements for traversing? */
    public boolean hasNext() {
      if (current < list.size()) {
        return true;
      }

      return false;
    }

    @Override
    /** Get the current element and move to the next */
    public E next() {
      return list.get(current++);
    }

    @Override
    /** Remove the current element */
    public void remove() {
      delete(list.get(current)); // Delete the current element
      list.clear(); // Clear the list
      inorder(); // Rebuild the list
    }
  }

  @Override
  public boolean isEmpty() {
    return size() == 0;
  }

  /**
   * Remove all elements from the tree
   */
  public void clear() {
    root = null;
    size = 0;
  }

  public static class TreeNode<E extends Comparable<E>> {
    protected E element;
    protected TreeNode<E> left;
    protected TreeNode<E> right;

    public TreeNode(E e) {
      element = e;
    }
  }
}