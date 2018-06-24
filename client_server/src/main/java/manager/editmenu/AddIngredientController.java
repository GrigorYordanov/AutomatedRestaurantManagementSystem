package manager.editmenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import manager.employeedata.Staff;

import org.apache.commons.lang3.math.NumberUtils;

import server.httprequest.HttpRequester;
import staff.menu.Ingredient;
import utilities.Navigation;

/**
 * The logic for adding an ingredient (and editing its cost). 
 * @author Mark Abdel Malak
 *
 */
public class AddIngredientController {

  @FXML
  private ResourceBundle resources;

  @FXML
  private URL location;

  @FXML
  private TableView<Ingredient> tableView;

  @FXML
  private TableColumn<Ingredient, String> nameCol1;

  @FXML
  private TableColumn<Ingredient, Float> costCol2;

  @FXML
  private TableColumn<Ingredient, Integer> caloriesCol3;

  @FXML
  private TableColumn<Ingredient, String> allergyCol4;

  @FXML
  private TableColumn<Ingredient, Integer> stockCol5;

  @FXML
  private TableColumn<Ingredient, String> metricCol6;

  @FXML
  private TextField nameField1;

  @FXML
  private TextField costField2;

  @FXML
  private TextField caloriesField3;

  @FXML
  private ComboBox<String> allergyField4;

  @FXML
  private TextField stockField5;

  @FXML
  private ComboBox<String> metricField6;

  private static HttpRequester httpRequester = new HttpRequester();

  private ObservableList<Ingredient> list = FXCollections.observableArrayList();
  
  private int refreshInterval = 10;
  
  /**
   * When the add button is pressed it adds an ingredient to the database.
   * @param event button click.
   * @throws NumberFormatException if there is an issue parsing the float.
   * @throws MalformedURLException if there is a URL issue with HttpRequester.
   * @throws IOException if there is an issue with HttpRequester.
   */
  @FXML
  void addIngredient(ActionEvent event)
      throws NumberFormatException, MalformedURLException, IOException {
    if (metricField6.getValue() != null 
        && allergyField4.getValue() != null 
        && !ingredientExists(nameField1.getText()) ) {
      Ingredient ingredient = new Ingredient(nameField1.getText(),
          Float.parseFloat(costField2.getText()), metricField6.getValue(),
          Integer.parseInt(caloriesField3.getText()), allergyField4.getValue(),
          Float.parseFloat(stockField5.getText()));
      list.add(ingredient);
      tableView.setItems(list);
      clearAllFields();
    }
  }
  
  /**
   * Used to clear all the input fields after successfully adding a ingredient.
   */
  private void clearAllFields() {
    nameField1.setText("");
    costField2.setText("");
    caloriesField3.setText("");
    allergyField4.getSelectionModel().clearSelection();
    stockField5.setText("");
    metricField6.getSelectionModel().clearSelection();
  }
  
  /**
   * Checks if there is another ingredient already in the database with the same name.
   * @param ingredientName name of the ingredient which is being checked.
   * @return true if there is an ingredient with this name.
   * @throws NumberFormatException if there is an issue parsing the float.
   * @throws MalformedURLException if there is an issue with the URL in HttpRequester.
   * @throws IOException if there is an issue with HttpRequester.
   */
  private boolean ingredientExists(String ingredientName)
      throws NumberFormatException, MalformedURLException, IOException {
    return Integer
        .parseInt(httpRequester
            .sendPost("/database/select",
                "SELECT COUNT(id) FROM ingredients_stock WHERE name = '" + ingredientName + "';")
            .split(" \n")[0]) > 0;
  }
  
  @FXML
  void initialize() throws MalformedURLException, IOException {
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    assert tableView != null : 
      "fx:id=\"tableView\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert nameCol1 != null : 
      "fx:id=\"nameCol1\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert costCol2 != null : 
      "fx:id=\"costCol2\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert caloriesCol3 != null : 
      "fx:id=\"caloriesCol3\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert allergyCol4 != null : 
      "fx:id=\"allergyCol4\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert stockCol5 != null : 
      "fx:id=\"stockCol5\" was not injected: check your FXML file 'fxml_tableview.fxml'.";
    assert metricCol6 != null : 
      "fx:id=\"metricCol6\" was not injected: check your FXML file 'fxml_tableview.fxml'.";

    TableColumn<Ingredient, String> nameCol1 = new TableColumn<>("Name");
    nameCol1.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));

    TableColumn<Ingredient, Float> costCol2 = new TableColumn<>("Cost");
    costCol2.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("cost"));
    costCol2.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
    TableColumn<Ingredient, Integer> caloriesCol3 = new TableColumn<>("Calories");
    caloriesCol3.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("calories"));

    TableColumn<Ingredient, String> allergyCol4 = new TableColumn<>("Allergy");
    allergyCol4.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("allergyName"));

    TableColumn<Ingredient, Float> stockCol5 = new TableColumn<>("Stock");
    stockCol5.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("quantity"));

    TableColumn<Ingredient, String> metricCol6 = new TableColumn<>("Metric");
    metricCol6.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("metricName"));
    // set up the ComboBox
    allergyField4.getItems().setAll(getAllAllergiesFromDb());
    // Set up the ComboBox
    metricField6.getItems().setAll(getMetricNameFromDb());

    // CODE STARTING FROM HERE IS FROM http://stackoverflow.com/a/32284751/7159163
    //Setting up columns so you can delete ingredients.
    TableColumn<Ingredient, Ingredient> deleteIngredientCol6 = new TableColumn();
    deleteIngredientCol6
        .setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

    deleteIngredientCol6.setCellFactory(param -> new TableCell<Ingredient, Ingredient>() {
      private final Button deleteButton = new Button("Delete");

      @Override
      protected void updateItem(Ingredient ingredient, boolean empty) {
        super.updateItem(ingredient, empty);
        if (ingredient == null) {
          setGraphic(null);
          return;
        }
        setGraphic(deleteButton);
        deleteButton.setOnAction(event -> {
          list.remove(ingredient);
          String sql = "delete from ingredients_stock where id =" + ingredient.getId();
          try {
            httpRequester.sendPost("/database/deleteRow", sql);
          } catch (MalformedURLException exception) {
            exception.printStackTrace();
          } catch (IOException exception) {
            exception.printStackTrace();
          }
        });
      }
    });
    // ENDING HERE

    tableView.setItems(list = new Ingredient().getAllIngredients());
    tableView.getColumns().addAll(nameCol1, costCol2, caloriesCol3, allergyCol4, stockCol5,
        metricCol6, deleteIngredientCol6);
    tableView.setEditable(true);
    
    costCol2.setOnEditCommit((CellEditEvent<Ingredient, Float> cell) -> {

      Ingredient ingredientToBeChanged = ((Ingredient) cell.getTableView().getItems()
          .get(cell.getTablePosition().getRow()));
      // Setting the price
      try {
        if (NumberUtils.isNumber(cell.getNewValue().toString())) {
          httpRequester.sendPost("/database/updatePrice",
              "ingredients_stock " + ingredientToBeChanged.getId() + " " + cell.getNewValue());
          
          ((Ingredient) cell.getTableView().getItems().get(cell.getTablePosition().getRow()))
          .setCost(cell.getNewValue());
        }
      } catch (IOException exception) {
        exception.printStackTrace();
      }

    });
    
    // Enables the GUI to refresh itself automatically after 10 seconds
    ScheduledExecutorService scheduledExecutor = Executors
          .newSingleThreadScheduledExecutor(new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
              Thread thread = new Thread(runnable);
              thread.setDaemon(true);
              return thread;
            }
          });

    scheduledExecutor.scheduleAtFixedRate(new Runnable() {

        @Override
        public void run() {
          final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                  try {
                    tableView.setItems(list = new Ingredient().getAllIngredients());
                  } catch (IOException exception) {
                    exception.printStackTrace();
                  }

                
                }
            };
          Platform.runLater(runnable);
        }
        
    }, 0, refreshInterval, TimeUnit.SECONDS);
    
  }

  /**
   * Performs a select but removes the space after the output.
   * @param selectStatement the select statement wanted.
   * @return an array of the answer to the query.
   */
  public static String[] selectFromDb(String selectStatement) {
    try {
      return httpRequester.sendPost("/database/select", selectStatement).split(" \n");
    } catch (MalformedURLException exception) {
      System.out.println("Server error: Incorrect URL");
    } catch (IOException exception) {
      System.out.println("Server error: Connection error");
    }
    return null;
  }

  /**
   * To produce the allergy ComboBox menu in the GUI.
   * 
   * @return all the allergy types in the database.
   */
  private static String[] getAllAllergiesFromDb() {
    return selectFromDb("SELECT name FROM ingredients_allergies;");
  }

  /**
   * To produce the metric drop down menu in the GUI.
   * 
   * @return all metric names that are in the database. E.g, kilograms or
   *         quantity or litres.
   */
  private static String[] getMetricNameFromDb() {
    return selectFromDb("SELECT name FROM metric_unit;");
  } 
  
  //DARREN CODE//
  private Staff thisManager;
  
  @FXML
  private ImageView backButton;

  public void initData(Staff user) {
    thisManager = user;
  }
  
  //Linking the application to main screen
  
  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manager Main Menu");
  }
  
  
  
}
