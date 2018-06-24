package manager.editmenu;

/** Copyright (c) 2012, 2014 Oracle and/or its affiliates.
 *  All rights reserved. Use is subject to license terms.
 *  http://docs.oracle.com/javafx/2/fxml_get_started/fxml_tutorial_intermediate.htm
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class AddIngredientMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Add Ingredient");
    Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("AddIngredientView.fxml"));
    Scene myScene = new Scene(myPane);
    primaryStage.setScene(myScene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}