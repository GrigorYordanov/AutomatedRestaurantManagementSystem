package customer.orderapplication;

/**
 * Controller class for the Customer ordering GUI.
 * @author Grigor Yordanov
 * @version 1.0
 */
import customer.order.Order;
import customer.viewmenu.ViewMenuDishes;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import utilities.Navigation;

public class CustomerOrderController implements Initializable {

  Order order;

  ScheduledExecutorService scheduledExecutorService;
  private int customerId;
  private int tableId;

  /**
   * Constructor to the controller
   * 
   * @param customerId
   *          - customerId is a variable.
   * 
   * @param tableId
   *          - tableId is a variable.
   * 
   */
  public CustomerOrderController(int customerId, int tableId) {
    this.customerId = customerId;
    this.tableId = tableId;
    this.order = new Order(tableId, customerId);
  }

  @FXML
  private TableView<ViewMenuDishes> menuTable;
  @FXML
  private TableColumn<ViewMenuDishes, Image> imageColumn;
  @FXML
  private TableColumn<ViewMenuDishes, String> nameColumn;
  @FXML
  private TableColumn<ViewMenuDishes, String> ingredientsColumn;
  @FXML
  private TableColumn<ViewMenuDishes, Float> caloriesColumn;
  @FXML
  private TableColumn<ViewMenuDishes, String> typeColumn;
  @FXML
  private TableColumn<ViewMenuDishes, String> allergyColumn;
  @FXML
  private TableColumn<ViewMenuDishes, String> dietaryColumn;
  @FXML
  private TableColumn<ViewMenuDishes, Float> priceColumn;
  @FXML
  private TableColumn<ViewMenuDishes, Boolean> btnCol;

  @FXML
  private TableView<ViewMenuDishes> orderTable;
  @FXML
  private TableColumn<ViewMenuDishes, String> nameOfItem1;
  @FXML
  private TableColumn<ViewMenuDishes, Float> costOfItem1;
  @FXML
  private TableColumn<ViewMenuDishes, Boolean> removeBut;

  @Override
  public void initialize(URL location, ResourceBundle resources) {

    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));

    /**
     * MENU TABLE Initializing the columns in the Menu Table.
     * 
     */
    imageColumn.setCellValueFactory(new PropertyValueFactory<>("image"));
    nameColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("name"));
    ingredientsColumn
        .setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("ingredients"));

    caloriesColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, Float>("calories"));
    typeColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("foodType"));
    allergyColumn
        .setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("allergyInfo"));

    dietaryColumn
        .setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("dietaryReq"));

    priceColumn.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, Float>("price"));

    /**
     * Initialize the "btnCol" column.
     */
    btnCol.setCellValueFactory(
        new Callback
        <TableColumn.CellDataFeatures<ViewMenuDishes, Boolean>, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue<Boolean> call(
              TableColumn.CellDataFeatures<ViewMenuDishes, Boolean> features) {
            return new SimpleBooleanProperty(features.getValue() != null);
          }
        });

    /**
     * Method which initialize and input on every row in the "Add" column a
     * button. The button is been displayed only if the row is not null.
     * 
     */
    btnCol.setCellFactory(
        new Callback<TableColumn<ViewMenuDishes, Boolean>, TableCell<ViewMenuDishes, Boolean>>() {
          @Override
          public TableCell<ViewMenuDishes, Boolean> call(
              TableColumn<ViewMenuDishes, Boolean> btnCol) {
            final StackPane paddedButton = new StackPane();
            final Button button = new Button("Add");
            button.setMinWidth(70);
            button.setStyle("-fx-background-color: #00ff00");
            paddedButton.getChildren().add(button);
            paddedButton.setPadding(new Insets(3));
            TableCell<ViewMenuDishes, Boolean> cell = new TableCell<ViewMenuDishes, Boolean>() {
              @Override

              protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                  setGraphic(button);
                } else {
                  setGraphic(null);

                }
              }
            };

            /**
             * A handler for the "Add" button which takes the selected row from
             * the "menuTable" and copy the row into the "OrderTable".
             */
            button.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                ViewMenuDishes dishFromMenu = menuTable.getItems().get(cell.getIndex());
                System.out.println(dishFromMenu.getName());
                order.addDish(dishFromMenu);
                orderTable.getItems().setAll(order.getDishList());
              }
            });

            return cell;
          }
        });

    /**
     * ORDER TABLE Initialize the columns for the Order Table.
     */

    nameOfItem1.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, String>("name"));
    costOfItem1.setCellValueFactory(new PropertyValueFactory<ViewMenuDishes, Float>("price"));

    /**
     * Initialize the "RemoveBut" column.
     */
    removeBut.setCellValueFactory(
        new Callback
        <TableColumn.CellDataFeatures<ViewMenuDishes, Boolean>, ObservableValue<Boolean>>() {
          @Override
          public ObservableValue<Boolean> call(
              TableColumn.CellDataFeatures<ViewMenuDishes, Boolean> features) {
            return new SimpleBooleanProperty(features.getValue() != null);
          }
        });

    /**
     * Method for showing the "Remove" button in the cell and when the button is
     * been clicked the handler remove the row from the table and from the DB.
     */
    removeBut.setCellFactory(
        new Callback<TableColumn<ViewMenuDishes, Boolean>, TableCell<ViewMenuDishes, Boolean>>() {
          @Override
          public TableCell<ViewMenuDishes, Boolean> call(
              TableColumn<ViewMenuDishes, Boolean> removeBut) {

            final StackPane paddedButton = new StackPane();
            final Button button = new Button("Remove");
            button.setMinWidth(115);
            button.setStyle("-fx-background-color: #FF0000");
            paddedButton.getChildren().add(button);
            paddedButton.setPadding(new Insets(3));
            TableCell<ViewMenuDishes, Boolean> cell = new TableCell<ViewMenuDishes, Boolean>() {
              @Override

              protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (!empty) {
                  setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                  setGraphic(button);
                } else {
                  setGraphic(null);

                }
              }
            };

            button.setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                ViewMenuDishes dishFromMenu = (ViewMenuDishes) cell.getTableRow().getItem();
                order.deleteDish(dishFromMenu);
                orderTable.getItems().setAll(order.getDishList());
              }
            });

            return cell;
          }
        });

    scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(

        new ThreadFactory() {
          @Override
          public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
          }
        });

    scheduledExecutorService.scheduleAtFixedRate(new Runnable() {

      @Override
      public void run() {
        final Runnable runnable = new Runnable() {

          @Override
          public void run() {
            ViewMenuDishes dishes = new ViewMenuDishes();
            ObservableList<ViewMenuDishes> viewMenu;

            try {
              orderTable.getItems().setAll(order.getDishList());
              viewMenu = dishes.getAllIngredients();
              menuTable.getItems().setAll(viewMenu);

            } catch (IOException exception) {
              exception.printStackTrace();
            }
          }
        };
        Platform.runLater(runnable);
      }

    }, 0, 10, TimeUnit.SECONDS);

  }

  @FXML
  private ImageView backButton;

  @FXML
  void navigateBack(MouseEvent event) {
    scheduledExecutorService.shutdown();
    Navigation.navigateToCustomer("/customer/customermainmenu/CustomerMainMenu.fxml",
        (Stage) backButton.getScene().getWindow(), this.customerId,
        this.tableId, "Customer Main Menu");
  }

}