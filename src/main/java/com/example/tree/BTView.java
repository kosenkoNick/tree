package com.example.tree;

import java.util.HashMap;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class BTView extends Pane {
  private final BST<Integer> tree;
  private final double vGap = 60; // Gap between two levels in a tree
  private HashMap<String, Circle> nodes = new HashMap<>();

  BTView(BST<Integer> tree) {
    this.tree = tree;
    setStatus("Tree is empty");
  }

  public void setStatus(String msg) {
    getChildren().add(new Text(20, 20, msg));
  }

  public void displayTree() {
    this.getChildren().clear(); // Clear the pane

    if (tree.getRoot() != null) {
      // Display tree recursively
      displayTree(tree.getRoot(), getWidth() / 2, vGap, getWidth() / 4);
//      nodes.forEach((k,v) -> System.out.println(k.getText() + v));
    }
  }

  private void displayTree(BST.TreeNode<Integer> current, double x, double y, double hGap) {
    if (current.left != null) {
      // Draw a line to the left node
      getChildren().add(new Line(x - hGap, y + vGap, x, y));
      // Draw the left subtree recursively
      displayTree(current.left, x - hGap, y + vGap, hGap / 2);
    }

    if (current.right != null) {
      // Draw a line to the right node
      getChildren().add(new Line(x + hGap, y + vGap, x, y));
      // Draw the right subtree recursively
      displayTree(current.right, x + hGap, y + vGap, hGap / 2);
    }

    // Display a node
    // Tree node radius
    double radius = 20;
    Circle circle = new Circle(x, y, radius);
    circle.setFill(Color.LIGHTGRAY);
    circle.setStroke(Color.BLACK);
    Text text = new Text(x - 4, y + 4, current.element + "");
    getChildren().addAll(circle, text);
    nodes.put(text.getText(), circle);
  }

  public void preorder() {
    displayTree();
    tree.preorder();
    highlight();
    setStatus("Preorder: " + tree.stack);
    signal();
  }

  public void inorder() {
    displayTree();
    tree.inorder();
    highlight();
    setStatus("Inorder: " + tree.stack);
  }

  public void postorder() {
    displayTree();
    tree.postorder();
    highlight();
    setStatus("Postorder: " + tree.stack);
  }

  private void highlight() {
    Thread thread = new Thread(() -> {
      for (Integer integer : tree.stack) {
        nodes.get(integer.toString()).setStroke(Color.RED);
        nodes.get(integer.toString()).setFill(Color.LIGHTCORAL);
        nodes.get(integer.toString()).setStrokeWidth(3);
        try {
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
        nodes.get(integer.toString()).setStroke(Color.BLACK);
        nodes.get(integer.toString()).setFill(Color.LIGHTGRAY);
        nodes.get(integer.toString()).setStrokeWidth(1);
      }
    });
    thread.start();
  }

  public void signal(){
//    Runnable thread = () -> {
      for (Circle circle : nodes.values()) {
        circle.setFill(Color.CORAL);
      }
//      try {
//        Thread.sleep(1000);
//      } catch (InterruptedException e) {
//        e.printStackTrace();
//      }
//      for (Circle circle : nodes.values()) {
//        circle.setFill(Color.LIGHTGRAY);
//      }
//    };
//    thread.run();
  }

}