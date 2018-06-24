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

/**
 * Main program for AddDish.
 * @author Mark Abdel Malak
 * @author James Harris
 */
public class AddDishMain extends Application {

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Add Dish View");
    Pane myPane = (Pane) FXMLLoader.load(getClass().getResource("AddDishView.fxml"));
    Scene myScene = new Scene(myPane);
    primaryStage.setScene(myScene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}