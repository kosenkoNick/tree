package com.example.tree;

import java.util.Stack;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BTView extends Pane {
  private BST<Integer> tree = new BST<>();
  private double radius = 20; // Tree node radius
  private double vGap = 60; // Gap between two levels in a tree
    private Stack<Integer> stack = new Stack<>();

  BTView(BST<Integer> tree) {
    this.tree = tree;
    setStatus("Tree is empty");
  }

  public void setStatus(String msg) {
    getChildren().add(new Text(20, 20, msg));
  }

  public void displayTree(String value) {
    this.getChildren().clear(); // Clear the pane
    if (tree.getRoot() != null) {
      // Display tree recursively
      displayTree(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 4, value);
    }
  }

  /**
   * Display a subtree rooted at position (x, y)
   */
  private void displayTree(BST.TreeNode<Integer> current, double x, double y, double hGap,
                           String value) {
    if (current.left != null) {
      // Draw a line to the left node
      getChildren().add(new Line(x - hGap, y + vGap, x, y));
      // Draw the left subtree recursively
      displayTree(current.left, x - hGap, y + vGap, hGap / 2, value);
    }

    if (current.right != null) {
      // Draw a line to the right node
      getChildren().add(new Line(x + hGap, y + vGap, x, y));
      // Draw the right subtree recursively
      displayTree(current.right, x + hGap, y + vGap, hGap / 2, value);
    }

    // Display a node
    Circle circle = new Circle(x, y, radius);
    if (value.isBlank()) {
      circle.setFill(Color.WHITE);
      circle.setStroke(Color.BLACK);
    } else {
      if (current.element.toString().equals(value)) {
        circle.setFill(Color.BLACK);
        circle.setStroke(Color.RED);
      }
    }
    getChildren().addAll(circle, new Text(x - 4, y + 4, current.element + ""));

  }

  public void preorder() {
    displayTree("");
    preorder(tree.getRoot());
    setStatus("Preorder: " + stack.toString());
    stack.clear();
  }
  protected void preorder(BST.TreeNode<Integer> current) {
    if (current == null) {
      return;
    }

//
//    Thread tr = new Thread(new Task<>() {
//      @Override
//      protected Object call() throws InterruptedException {
//
//        displayTree(current.element.toString());
//        Thread.sleep(1000);
//
//        return null;
//      }
//    });
//    tr.start();
//    try {
//      tr.join();
//    } catch (InterruptedException e) {
//      e.printStackTrace();
//    }
    stack.add(current.element);
    highlightElement(current.element.toString());
    preorder(current.left);
    preorder(current.right);
  }


  public void inorder() {
    displayTree("");
    inorder(tree.getRoot());
    setStatus("Inorder: " + stack.toString());
    stack.clear();
  }
  protected void inorder(BST.TreeNode<Integer> current) {
    if (current == null) {
      return;
    }
    inorder(current.left);
    stack.add(current.element);
    highlightElement(current.element.toString());
    inorder(current.right);
  }


  public void postorder() {
    displayTree("");
    postorder(tree.getRoot());
    setStatus("Postorder: " + stack.toString());
    stack.clear();
  }
  protected void postorder(BST.TreeNode<Integer> current) {
    if (current == null) {
      return;
    }
    postorder(current.left);
    postorder(current.right);
    stack.add(current.element);
    highlightElement(current.element.toString());
  }

  void highlightElement(String value){
    for (Node node : getChildren()){
      if(node instanceof Text){
        if (((Text)node).getText().equals(value)) {
          ((Text) node).setFill(Color.RED);
        }
      }
    }
  }
}