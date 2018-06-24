package kitchen.kitchenapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class KitchenMain extends Application {
  
  public static void main(String[] args) {
    Application.launch(KitchenMain.class, args);
  }
    
  @Override
  public void start(Stage mainStage) throws Exception {
    
    Pane mainPane = (Pane) FXMLLoader.load(getClass().getResource("KitchenView.fxml"));
    Scene scene = new Scene(mainPane);
    mainStage.setTitle("Kitchen Options");
    mainStage.setScene(scene);
    mainStage.show();
  }
}
