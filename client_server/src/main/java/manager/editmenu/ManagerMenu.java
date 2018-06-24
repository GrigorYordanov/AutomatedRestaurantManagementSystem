package manager.editmenu;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import manager.employeedata.Staff;

import org.apache.commons.lang3.math.NumberUtils;
import server.httprequest.HttpRequester;
import staff.menu.Dish;
import utilities.Navigation;

/**
 * Manager's menu so they can edit the cost of dishes.
 * @author Mark Abdel Malak
 *
 */

//Tutorial used for this http://docs.oracle.com/javafx/2/ui_controls/table-view.htm

public class ManagerMenu extends Application {
  
  //-------DARREN CODE---------//
  private Staff thisManager;
  final ImageView backButton = new ImageView("file:res/images/ic_arrow_back_black_48dp_2x.png");
  
  public ManagerMenu(Staff manager) {
    this.thisManager = manager;
  }

  private void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), thisManager, "Manager Menu");
  }
  
  //--------------------------//
  
  private TableView<Dish> table = new TableView<Dish>();
  private HttpRequester httpRequester = new HttpRequester();

  Dish dish = new Dish();
  ObservableList<Dish> menu;

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws MalformedURLException, IOException {
    final VBox vbox = new VBox();
    
    //add a back button
    backButton.setFitWidth(32.0);
    backButton.setFitHeight(29.0);
    vbox.getChildren().add(backButton);
    backButton.setOnMouseClicked(this::navigateBack);
    
    Label invalidPrice = new Label();
    invalidPrice.setText("");
    invalidPrice.setTextFill(Color.RED);
    vbox.getChildren().add(invalidPrice);
    stage.setTitle("Manager view");

    //stage.setWidth(400);
    //stage.setHeight(520);
    
    final Label label = new Label("Manager view");
    label.setFont(new Font("Arial", 20));
    table.setMinWidth(300);
    table.setEditable(true);

    menu = dish.getAllDishes();

    TableColumn<Dish, String> nameCol = new TableColumn<>("Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<Dish, String>("name"));

    TableColumn<Dish, Float> costCol = new TableColumn<>("Price");
    costCol.setCellValueFactory(new PropertyValueFactory<Dish, Float>(String.valueOf("cost")));

    costCol.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
    costCol.setOnEditCommit((CellEditEvent<Dish, Float> t) -> {
      if (NumberUtils.isNumber(t.getNewValue().toString()) && t.getNewValue() > 0f ) {
        Dish dishToBeChanged = ((Dish) t.getTableView().getItems()
            .get(t.getTablePosition().getRow()));
        // Setting the price
        try {
          httpRequester.sendPost("/database/updatePrice",
              "dishes " + dishToBeChanged.getId() + " " + t.getNewValue());
          ((Dish) t.getTableView().getItems().get(t.getTablePosition().getRow()))
              .setCost(t.getNewValue());
          if (t.getNewValue() < Dish.findMinimumPrice(dishToBeChanged)) {
            invalidPrice.setText(dishToBeChanged.getName() + " does not generate enough profit.");
          } else {
            invalidPrice.setText("");
          }
        } catch (IOException exception) {
          exception.printStackTrace();
        }
      } else {
        ((Dish) t.getTableView().getItems()
            .get(t.getTablePosition().getRow())).setCost(t.getOldValue());
        invalidPrice.setText("INVALID COST the database has not been updated");
      }
    });

    // CODE STARTING FROM HERE IS FROM http://stackoverflow.com/a/32284751/7159163
    //Allows dishes to be deleted.
    TableColumn<Dish, Dish> deleteIngredientCol3 = new TableColumn();
    deleteIngredientCol3
        .setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

    deleteIngredientCol3.setCellFactory(param -> new TableCell<Dish, Dish>() {
      private final Button deleteButton = new Button("Delete");

      @Override
      protected void updateItem(Dish dish, boolean empty) {
        super.updateItem(dish, empty);
        if (dish == null) {
          setGraphic(null);
          return;
        }
        setGraphic(deleteButton);
        deleteButton.setOnAction(event -> {
          try {
            httpRequester.sendPost("/database/deleteRow",
                "delete from dishes_ingredients where dish_id = " + dish.getId() + ";");
            httpRequester.sendPost("/database/deleteRow",
                "delete from dishes where id =" + dish.getId());
          } catch (MalformedURLException exception) {
            return;
          } catch (IOException exception) {
            return;
          }
          menu.remove(dish);
        });
      }
    });
    // ENDING HERE
    table.setItems(menu);
    table.getColumns().addAll(nameCol, costCol, deleteIngredientCol3);

    vbox.setSpacing(7);
    vbox.setPadding(new Insets(10, 0, 0, 10));
    vbox.getChildren().addAll(table);
    

    Scene scene = new Scene(new Group());
    ((Group) scene.getRoot()).getChildren().addAll(vbox);

    stage.setScene(scene);
    stage.show();
  }
}