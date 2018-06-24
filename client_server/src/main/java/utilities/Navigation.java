package utilities;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import customer.orderapplication.CustomerOrderController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import manager.employeedata.Staff;

public class Navigation {

  /**
   * This method is used to navigate between guis.
   * 
   * @param fxmlFile
   *          The file URL of the FXML you want to show.
   * @param stage
   *          The Stage where the FXML file will be loaded in to.
   * @param title
   *          The title of the stage being navigated to.
   */
  public static void navigateTo(String fxmlFile, Stage stage, String title) {
    try {
      // load FXML
      FXMLLoader loader = new FXMLLoader(
          Navigation.class.getResource(fxmlFile));
      Parent root = (Parent) loader.load();
      // Show the FXML
      stage.setScene(new Scene(root));
      stage.setTitle(title);
      stage.show();
    } catch (IOException ioE) {
      System.out.println("Could not load the FXML file :" + fxmlFile);
    }
  }

  /**
   * This method is used to navigate to a staff gui by passing in staff object.
   * 
   * @param fxmlFile
   *          The FXML to load
   * @param stage
   *          The stage which FXML will be loaded on
   * @param thisUser
   *          The staff object to pass in.
   * @param title
   *          The title of the stage being navigated to.
   */
  public static void navigateToStaff(String fxmlFile, Stage stage,
      Staff thisUser, String title) {
    
    //argument types for the controller
    @SuppressWarnings("rawtypes")
    Class[] arguments = new Class[1];
    arguments[0] = Staff.class;

    try {
      FXMLLoader loader = new FXMLLoader(
          Navigation.class.getResource(fxmlFile));
      Parent root = (Parent) loader.load();

      //get the init method from which ever controller is loaded
      Method init = loader.getController().getClass().getMethod("initData",
          arguments);
      //run the method passing in the correct object
      init.invoke(loader.getController(), thisUser);

      stage.setScene(new Scene(root));
      stage.setTitle(title);
      stage.show();
      
    } catch (IOException ioE) {
      System.out.println("Could not load the FXML file");
      ioE.printStackTrace();
    } catch (NoSuchMethodException nsmE) {
      System.out.println("This method does not exist");
    } catch (IllegalAccessException iaE) {
      System.out.println("Cannont access Method");
    } catch (InvocationTargetException itE) {
      System.out.println("Error invocing the target");
    }

  }
  
  /**
   * This method is to navigate to the order Screen. This requires a unique
   * method since the controller must be disconnected.
   * 
   * @param fxmlFile
   *          The FXMLfile location
   * @param stage
   *          The stage where to load the gui
   * @param customerId
   *          The id of the customer
   * @param tabletId
   *          The id of the tablet
   * @param title
   *          The title of the stage being navigated to.
   */
  public static void navigateToOrder(String fxmlFile, Stage stage,
      int customerId, int tabletId, String title) {
    try {
      // get the loader
      FXMLLoader loader = new FXMLLoader(
          Navigation.class.getResource(fxmlFile));

      // make the controller and add to gui
      CustomerOrderController controller = new CustomerOrderController(
          customerId, tabletId);
      loader.setController(controller);

      Parent root = (Parent) loader.load();

      stage.setScene(new Scene(root));
      stage.setTitle(title);
      stage.show();
    } catch (IOException ioE) {
      System.out.println("Cannot find file");
    }
  }

  /**
   * This method is used to navigate around the customer guis with 2 parameters.
   * 
   * @param fxmlFile
   *          The fxml file to load
   * @param stage
   *          The stage where to load it
   * @param customerId
   *          The id of the customer
   * @param tabletId
   *          The id of the tablet
   * @param title
   *          The title of the stage being navigated to.
   */
  public static void navigateToCustomer(String fxmlFile, Stage stage,
      int customerId, int tabletId, String title) {
    
    @SuppressWarnings("rawtypes")
    Class[] arguments = new Class[2];
    arguments[0] = int.class;
    arguments[1] = int.class;
    
    try {
      FXMLLoader loader = new FXMLLoader(
          Navigation.class.getResource(fxmlFile));
      Parent root = (Parent) loader.load();

      //get the init method from which ever controller is loaded
      Method init = loader.getController().getClass().getMethod("initData",
          arguments);
      
      //run the method passing in the correct object
      init.invoke(loader.getController(), new Object[]{customerId, tabletId});

      stage.setScene(new Scene(root));
      stage.setTitle(title);
      stage.show();
      
    } catch (IOException ioE) {
      System.out.println("Could not load the FXML file");
    } catch (NoSuchMethodException nsmE) {
      System.out.println("This method does not exist");
    } catch (IllegalAccessException iaE) {
      System.out.println("Cannont access Method");
    } catch (InvocationTargetException itE) {
      System.out.println("Error invocing the target");
    }
    
  }
}
