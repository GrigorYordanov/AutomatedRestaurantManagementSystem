package manager.editmenu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class EditMenuMain extends Application {
  
  public static void main(String[] args) {
    Application.launch(EditMenuMain.class, args);
  }
    
  @Override
  public void start(Stage mainStage) throws Exception {
    
    Pane mainPane = (Pane) FXMLLoader.load(getClass().getResource("EditMenuView.fxml"));
    Scene scene = new Scene(mainPane);
    mainStage.setTitle("Edit Menu");
    mainStage.setScene(scene);
    mainStage.show();
  }
}
