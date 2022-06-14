package com.example.tree;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class BSTAnimation extends Application {
  Button btInsert = new Button("Insert");
  Button btDelete = new Button("Delete");
  Button btPreorder = new Button("Preorder");
  Button btInorder = new Button("Inorder");
  Button btPostorder = new Button("Postorder");

  @Override
  public void start(Stage primaryStage) {
    BST<Integer> tree = new BST<>(); // Create a tree
    tree.insert(10);
    tree.insert(15);
    tree.insert(20);
    tree.insert(30);
    tree.insert(37);
    tree.insert(-11);
    tree.insert(-5);
    tree.insert(-1);
    tree.insert(-28);
    tree.insert(-27);
    tree.insert(-42);

    BorderPane pane = new BorderPane();
    BTView view = new BTView(tree); // Create a BTView
    pane.setCenter(view);

    TextField tfKey = new TextField();
    tfKey.setPrefColumnCount(3);

    HBox hBox = new HBox(5, new Label("Enter a key: "), tfKey, btInsert, btDelete, btPreorder, btInorder, btPostorder);
    hBox.setAlignment(Pos.CENTER);
    hBox.setOpacity(10);
    hBox.setPadding(new Insets(0, 0, 10, 0));
    pane.setBottom(hBox);

    btInsert.setOnAction(e -> {
      int key = Integer.parseInt(tfKey.getText());
      if (tree.isPresent(key)) { // key is in the tree already
        view.displayTree();
        view.setStatus(key + " is already in the tree");
      } else {
        tree.insert(key); // Insert a new key
        view.displayTree();
        view.setStatus(key + " is inserted in the tree");
      }
    });

    btDelete.setOnAction(e -> {
      int key = Integer.parseInt(tfKey.getText());
      if (!tree.isPresent(key)) { // key is not in the tree
        view.displayTree();
        view.setStatus(key + " is not in the tree");
      } else {
        tree.delete(key); // Delete a key
        view.displayTree();
        view.setStatus(key + " is deleted from the tree");
      }
    });

    btPreorder.setOnAction(e -> {
      buttonsActive(true);
      view.preorder();
      buttonsActive(false);
    });
    btInorder.setOnAction(e -> {
      buttonsActive(true);
      view.inorder();
      buttonsActive(false);
    });
    btPostorder.setOnAction(e -> {
      buttonsActive(true);
      view.postorder();
      buttonsActive(false);
    });

    // Create a scene and place the pane in the stage
    Scene scene = new Scene(pane, 500, 400);
    primaryStage.setTitle("Kosenko Mykyta KND - 22"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
    view.displayTree();
  }

  public void buttonsActive(boolean choice){
    btDelete.setDisable(choice);
    btInsert.setDisable(choice);
    btInorder.setDisable(choice);
    btPostorder.setDisable(choice);
    btPreorder.setDisable(choice);
  }
}