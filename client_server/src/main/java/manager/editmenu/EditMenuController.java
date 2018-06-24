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
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import manager.employeedata.Staff;
import server.httprequest.HttpRequester;
import staff.menu.Dish;
import staff.menu.Ingredient;
import utilities.Navigation;

/**
 * This is the controller class used to add functionality to the Edit Menu GUI.
 * 
 * @author Christos Dexiades
 * 
 * @see EditMenuMain
 */

public class EditMenuController implements Initializable {

  ScheduledExecutorService scheduledExecutor;
  private Staff thisManager;

  /**
   * Method to pass the logged in manager from the previous application screen.
   * 
   * @param user
   *          the manager for this session
   * 
   * @author Darren Matthews
   */

  public void initData(Staff user) {
    this.thisManager = user;
  }

  @FXML
  private ImageView backButton;

  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutor.shutdown();
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), this.thisManager, "Manager Main Menu");
  }

  // Global variables

  ObservableList<Dish> dishes = null;
  ObservableList<Ingredient> ingredientsList = null;
  ObservableList<Ingredient> unusedIngredientsList = null;
  int selectedDishId = 1;
  int refreshInterval = 10;
  private HttpRequester httpRequester = new HttpRequester();

  @FXML
  Pane mainPane;

  // FXML controls for the 'Used Ingredients' table

  @FXML
  TableView<Ingredient> usedIngredients = new TableView<Ingredient>();

  @FXML
  TableColumn<Ingredient, Integer> usedIdCol = new TableColumn<Ingredient, Integer>(
      "Ingredient ID");

  @FXML
  TableColumn<Ingredient, String> usedNameCol = new TableColumn<Ingredient, String>("Name");

  @FXML
  TableColumn<Ingredient, Float> usedQtyCol = new TableColumn<Ingredient, Float>("Quantity");

  @FXML
  TableColumn<Ingredient, Boolean> removeFromDishCol = new TableColumn<Ingredient, Boolean>(
      "Remove");

  // FXML controls for the 'Unused Ingredients' table

  @FXML
  TableView<Ingredient> unusedIngredients = new TableView<Ingredient>();

  @FXML
  TableColumn<Ingredient, Integer> unusedIdCol = new TableColumn<Ingredient, Integer>(
      "Ingredient ID");

  @FXML
  TableColumn<Ingredient, String> unusedNameCol = new TableColumn<Ingredient, String>("Name");

  @FXML
  TableColumn<Ingredient, Float> unusedQtyCol = new TableColumn<Ingredient, Float>("Quantity");

  @FXML
  TableColumn<Ingredient, Boolean> addToDishCol = new TableColumn<Ingredient, Boolean>("Add");

  // FXML controls for selecting and editing a dish

  @FXML
  ChoiceBox dishSelector;

  // FXML button for refreshing the GUI

  @FXML
  Button refresh;

  @FXML
  private Label invalidPrice;

  private boolean dishExists(String dishName)
      throws NumberFormatException, MalformedURLException, IOException {
    return Integer
        .parseInt(httpRequester
            .sendPost("/database/select",
                "SELECT COUNT(id) FROM dishes WHERE name = '" + dishName + "';")
            .split(" \n")[0]) > 0;
  }

  public void refresh(EventHandler<ActionEvent> event) {
    refresh.setOnAction(event);
  }

  @Override
  public void initialize(URL arg0, ResourceBundle arg1) {
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));

    // Keeps the columns in the tables evenly sized

    usedIngredients.setColumnResizePolicy(usedIngredients.CONSTRAINED_RESIZE_POLICY);
    unusedIngredients.setColumnResizePolicy(usedIngredients.CONSTRAINED_RESIZE_POLICY);

    // Initialises the columns in the 'Used Ingredients' table

    usedIdCol.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("id"));
    usedNameCol.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
    usedQtyCol.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("quantity"));

    removeFromDishCol.setCellValueFactory(
        new Callback
        <TableColumn.CellDataFeatures<Ingredient, Boolean>, ObservableValue<Boolean>>() {

          @Override
          public ObservableValue<Boolean> call(
              TableColumn.CellDataFeatures<Ingredient, Boolean> features) {
            return new SimpleBooleanProperty(features.getValue() != null);
          }
        });

    removeFromDishCol.setCellFactory(
        new Callback<TableColumn<Ingredient, Boolean>, TableCell<Ingredient, Boolean>>() {

          @Override
          public TableCell<Ingredient, Boolean> call(
              TableColumn<Ingredient, Boolean> unitBooleanTableColumn) {
            return new RemoveFromDishCell(usedIngredients);
          }
        });

    // Initialises the columns in the 'Unused Ingredients' table

    unusedIdCol.setCellValueFactory(new PropertyValueFactory<Ingredient, Integer>("id"));
    unusedNameCol.setCellValueFactory(new PropertyValueFactory<Ingredient, String>("name"));
    unusedQtyCol.setCellValueFactory(new PropertyValueFactory<Ingredient, Float>("quantity"));

    addToDishCol.setCellValueFactory(
        new Callback
        <TableColumn.CellDataFeatures<Ingredient, Boolean>, ObservableValue<Boolean>>() {

          @Override
          public ObservableValue<Boolean> call(
              TableColumn.CellDataFeatures<Ingredient, Boolean> features) {
            return new SimpleBooleanProperty(features.getValue() != null);
          }
        });

    addToDishCol.setCellFactory(
        new Callback<TableColumn<Ingredient, Boolean>, TableCell<Ingredient, Boolean>>() {

          @Override
          public TableCell<Ingredient, Boolean> call(
              TableColumn<Ingredient, Boolean> unitBooleanTableColumn) {
            return new AddToDishCell(unusedIngredients);
          }
        });

    // Adds all columns defined above to the relevant tables that is displayed
    // on the GUI

    usedIngredients.getColumns().addAll(usedIdCol, usedNameCol, usedQtyCol, removeFromDishCol);
    unusedIngredients.getColumns().addAll(unusedIdCol, unusedNameCol, unusedQtyCol, addToDishCol);

    // Listens for a change in the selected dish in the dish selector ChoiceBox

    dishSelector.getSelectionModel().selectedItemProperty()
        .addListener(new ChangeListener<String>() {

          @Override
          public void changed(ObservableValue arg0, String arg1, String arg2) {
            String[] displayedDishData = arg2.split(" ");
            selectedDishId = Integer.parseInt(displayedDishData[0]);
          }

        });

    // Initial call to populate the 'Ingredients' table with data

    updateDishesPanel();

    // Adds functionality to the refresh button to allow manual reloading of the
    // GUI

    refresh(new EventHandler<ActionEvent>() {

      @Override
      public void handle(ActionEvent event) {
        refreshUserInterface();
      }
    });

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
            refreshUserInterface();
          }
        };
        Platform.runLater(runnable);
      }

    }, 0, refreshInterval, TimeUnit.SECONDS);

  }

  private String[] getAllDietaryRequirementsFromDb() throws MalformedURLException, IOException {
    return httpRequester.sendPost("/database/select", "SELECT name from dietary_requirements;")
        .split(" \n");
  }

  private String[] getAllFoodTypesFromDb() throws MalformedURLException, IOException {
    return httpRequester.sendPost("/database/select", "select name from food_type;").split(" \n");
  }

  /**
   * This method is called by the initialize method to set up the 'Edit Menu'
   * tab and is assigned to the refresh button on this tab to manually update
   * the tables.
   */

  public void updateDishesPanel() {

    Dish dish = new Dish();

    // Adds all dishes to the ChoiceBox

    try {
      dishes = dish.getAllDishes();
    } catch (IOException exception) {
      System.out.println("Edit Menu GUI: could not retrieve a list of dishes!");
    }

    dishSelector.getItems().clear();

    for (Dish d : dishes) {
      dishSelector.getItems().addAll(d.getId() + " " + d.getName() + " £" + d.getCost());
    }

    Ingredient ing = new Ingredient();

    // Adds all ingredients to the appropriate tables.

    try {
      ingredientsList = ing.getIngredientsById(selectedDishId);
      unusedIngredientsList = ing.getAllIngredients();
    } catch (IOException exception) {
      System.out.println("Edit Menu GUI: a list of ingredients could not be retrieved!");
    }

    usedIngredients.setItems(ingredientsList);
    unusedIngredients.setItems(unusedIngredientsList);

  }

  /**
   * Method is called each time the refresh button or the automatic refresh
   * feature is used.
   */

  public void refreshUserInterface() {
    updateDishesPanel();
  }

  private class AddToDishCell extends TableCell<Ingredient, Boolean> {

    // A button to add the ingredient to the dish

    final Button addButton = new Button("Add");

    // Pads and centers the button within the cell in TableView

    final StackPane paddedButton = new StackPane();

    /**
     * Constructor to create a new AddToDishCell object
     * 
     * @param table
     *          The TableView displaying the list of unused ingredients.
     */

    AddToDishCell(final TableView<Ingredient> table) {
      paddedButton.setPadding(new Insets(3));
      paddedButton.getChildren().add(addButton);
      addButton.setStyle("-fx-background-color: #00ff00");
      addButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {

          Ingredient selectedIngredient = table.getItems().get(getIndex());

          try {
            selectedIngredient.addIngredientToDish(selectedDishId);
            unusedIngredients.getItems().add(selectedIngredient);
          } catch (IOException expected) {
            // DO NOTHING
          }
        }
      });
    }

    // Places a button in the row if and only if the row is not empty

    @Override
    protected void updateItem(Boolean item, boolean empty) {

      super.updateItem(item, empty);

      if (!empty) {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(paddedButton);
      } else {
        setGraphic(null);
      }
    }
  }

  private class RemoveFromDishCell extends TableCell<Ingredient, Boolean> {

    // A button to remove the ingredient from the dish

    final Button removeButton = new Button("Remove");

    // Pads and centres the button within the cell in TableView

    final StackPane paddedButton = new StackPane();

    /**
     * Constructor to create a new AddToDishCell object
     * 
     * @param table
     *          The TableView displaying the list of used ingredients.
     */

    RemoveFromDishCell(final TableView<Ingredient> table) {
      paddedButton.setPadding(new Insets(3));
      paddedButton.getChildren().add(removeButton);
      removeButton.setStyle("-fx-background-color: #ff0000");
      removeButton.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent actionEvent) {

          Ingredient selectedIngredient = table.getItems().get(getIndex());

          try {
            selectedIngredient.removeIngredientFromDish(selectedDishId);
            usedIngredients.getItems().remove(selectedIngredient);
          } catch (IOException expected) {
            // DO NOTHING
          }
        }
      });
    }

    // Places a button in the row if and only if the row is not empty

    @Override
    protected void updateItem(Boolean item, boolean empty) {

      super.updateItem(item, empty);

      if (!empty) {
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        setGraphic(paddedButton);
      } else {
        setGraphic(null);
      }
    }
  }

}