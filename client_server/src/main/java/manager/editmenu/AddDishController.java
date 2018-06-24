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
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import manager.employeedata.Staff;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import server.httprequest.HttpRequester;
import staff.menu.Dish;
import staff.menu.Ingredient;
import utilities.Navigation;

/**
 * The logic behind allowing the manager to add new dishes. 
 * @author Mark Abdel Malak
 * @author James Harris
 */

public class AddDishController {

  ScheduledExecutorService scheduledExecutor;

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
  private TextField dishNameField0;

  @FXML
  private Button makeNewDishButton;

  @FXML
  private TextField costField1;

  private ObservableList<Ingredient> list;

  @FXML
  private TextField urlField2;

  @FXML
  private ComboBox<String> foodTypeField3;

  @FXML
  private TextField ingredientsField7;

  @FXML
  private Label invalidPrice;

  @FXML
  private ComboBox<String> dietaryRequirementField4;

  private static HttpRequester httpRequester = new HttpRequester();

  private int refreshInterval = 10;

  @FXML
  void makeNewDish() throws NumberFormatException, MalformedURLException, IOException {
    if (isValidDishDetails()) {
      int mealDealId;
      if (foodTypeField3.getValue().equals("Meal_Deal")) {
        mealDealId = Integer.parseInt(httpRequester
            .sendPost("/database/select", "SELECT MAX(mealdeal_id) FROM dishes;").split(" \n")[0])
            + 1;

        new Dish(dishNameField0.getText(), Float.parseFloat(costField1.getText()),
            urlField2.getText(), foodTypeField3.getValue(), dietaryRequirementField4.getValue(),
            mealDealId);
      } else {
        new Dish(dishNameField0.getText(), Float.parseFloat(costField1.getText()),
            urlField2.getText(), foodTypeField3.getValue(), dietaryRequirementField4.getValue(), 0);
      }
      int dishId = Integer.parseInt(httpRequester
          .sendPost("/database/select", "SELECT MAX(id) from dishes;").split(" \n")[0]);

      String[] listOfIngredients = ingredientsField7.getText().split(", ");
      int numberOfIngredients = listOfIngredients.length;
      for (int countIds = 0; countIds < numberOfIngredients; countIds++) {

        httpRequester.sendPost("/database/insertRow", "INSERT INTO dishes_ingredients VALUES("
            + dishId + ", " + listOfIngredients[countIds] + ", " + "1" + ");");
      }
      clearAllFields();
    }
  }

  private boolean isValidDishDetails()
      throws NumberFormatException, MalformedURLException, IOException {
    return (StringUtils.isAlpha(dishNameField0.getText()) && !dishExists(dishNameField0.getText())
        && NumberUtils.isNumber(costField1.getText()) && isValidDishPrice()
        && urlField2.getText().startsWith("http://") && foodTypeField3.getValue() != null
        && dietaryRequirementField4.getValue() != null
        && !ingredientsField7.getText().equals(""));
  }

  private boolean isValidDishPrice() {
    if (Float.parseFloat(costField1.getText()) > 0f) {
      invalidPrice.setText("");
      return true;
    } else {
      invalidPrice.setTextFill(Color.RED);
      invalidPrice.setText("INVALID DISH COST add failed");
      return false;
    }
  }

  private boolean dishExists(String dishName)
      throws NumberFormatException, MalformedURLException, IOException {
    return Integer
        .parseInt(httpRequester
            .sendPost("/database/select",
                "SELECT COUNT(id) FROM dishes WHERE name = '" + dishName + "';")
            .split(" \n")[0]) > 0;
  }

  private void clearAllFields() {
    dishNameField0.setText("");
    costField1.setText("");
    urlField2.setText("");
    foodTypeField3.getSelectionModel().clearSelection();
    dietaryRequirementField4.getSelectionModel().clearSelection();
    ingredientsField7.setText("");
  }

  private String[] getAllDietaryRequirementsFromDb() throws MalformedURLException, IOException {
    return httpRequester.sendPost("/database/select", "SELECT name from dietary_requirements;")
        .split(" \n");
  }

  private String[] getAllFoodTypesFromDb() throws MalformedURLException, IOException {
    return httpRequester.sendPost("/database/select", "select name from food_type;").split(" \n");
  }

  @FXML
  void initialize() throws MalformedURLException, IOException {

    invalidPrice.setTextFill(Color.RED);
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));

    TableColumn<Ingredient, Integer> idCol1 = new TableColumn<>("id");
    idCol1.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("id"));

    TableColumn<Ingredient, String> nameCol2 = new TableColumn<>("Name");
    nameCol2.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));

    TableColumn<Ingredient, Float> costCol3 = new TableColumn<>("Price");
    costCol3.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("cost"));

    try {
      foodTypeField3.getItems().setAll(getAllFoodTypesFromDb());
      dietaryRequirementField4.getItems().setAll(getAllDietaryRequirementsFromDb());
      System.out.println(getAllDietaryRequirementsFromDb());
      System.out.println(getAllFoodTypesFromDb());
    } catch (MalformedURLException exception) {
      exception.printStackTrace();
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    // set up the ComboBox
    foodTypeField3.getItems().setAll(getAllFoodTypesFromDb());
    dietaryRequirementField4.getItems().setAll(getAllDietaryRequirementsFromDb());
    list = new Ingredient().getAllIngredients();
    tableView.setItems(list);
    tableView.getColumns().addAll(idCol1, nameCol2, costCol3);

    // Enables the GUI to refresh itself automatically after 10 seconds

    scheduledExecutor = Executors.newSingleThreadScheduledExecutor(new ThreadFactory() {
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
   * 
   * @param selectStatement
   *          the select statement wanted.
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

  // DARREN CODE//
  private Staff thisManager;

  @FXML
  private ImageView backButton;

  public void initData(Staff user) {
    thisManager = user;
  }

  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutor.shutdown();
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manager Main Menu");
  }

}
