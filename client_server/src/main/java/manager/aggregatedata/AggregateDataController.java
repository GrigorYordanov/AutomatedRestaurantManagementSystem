 
/**
  * The package 'aggregatedata' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the manager can view aggregate data of the restaurant.
  */

package manager.aggregatedata;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import manager.employeedata.Staff;
import utilities.Navigation;

/**
 * The class 'AggregateDataController' is used to fill the FXML file with data
 * from the 'AggregateData' class.
 * @author Svetoslav Mihovski
 */
public class AggregateDataController
    implements Initializable {

  //FUNCTIONALITIES FOR GUI
  @FXML private Button calculateButton;
  @FXML private DatePicker fromCalendar;
  @FXML private DatePicker toCalendar;

  //Injecting values from FXML loader
  
  //AVARAGE DATA FOR WAITER
  @FXML private TableView<AggregateData> waiterTable;

  @FXML private TableColumn<AggregateData,String> wnameColumn;
  @FXML private TableColumn<AggregateData,Integer> wordersColumn;
  @FXML private TableColumn<AggregateData,Float> wsalesColumn;
  @FXML private TableColumn<AggregateData,String> wtconfirmColumn;
  @FXML private TableColumn<AggregateData,String> wtdeliverColumn;
  @FXML private TableColumn<AggregateData,String> wtcompleteColumn;
  
  //OVERALL DATA FOR RESTOURANT
  @FXML private TextField rordersField;
  @FXML private TextField rsalesField;
  @FXML private TextField rtconfirmField;
  @FXML private TextField rtdeliverField;
  @FXML private TextField rtcompleteField;

  /**
   * Method used to get the current local date.
   * @return returns the current local date.
   */
  public static final LocalDate localDate() {
    
    String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate localDate = LocalDate.parse(date , formatter);
    return localDate;
  }
   
  /**
   * Method used to calculate the days between two dates chosen from DatePicker.
   * @param d1 and d2 chose between two intervals.
   * @return difference between the two state intervals.
   */
  public static int getDifferenceDays(DatePicker d1, DatePicker d2) {
    
    long date1 = d1.getValue().toEpochDay();
    long date2 = d2.getValue().toEpochDay();
    int  days  = (int) Math.abs(date1 - date2);
    return days + 1;
  }
   
  /**
   * Method used to calculate the sum of two time variables.
   * @param time1 and time2 used to calculate their sum.
   * @return returns the sum of two times.
   */
  @SuppressWarnings("deprecation")
  public static String sumTimes(Time time1, Time time2) {
    
    int t1Seconds = 0;
    int t2Seconds = 0;
    int sumSeconds = 0;
        
    t1Seconds = time1.getHours() * 3600 + time1.getMinutes() * 60 + time1.getSeconds();
    t2Seconds = time2.getHours() * 3600 + time2.getMinutes() * 60 + time2.getSeconds();

    sumSeconds = t1Seconds + t2Seconds;
    int hours = sumSeconds / 3600;
    int minutes = (sumSeconds % 3600) / 60;
    int seconds = sumSeconds % 60;

    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
  
  /**
   * Method used to break down time variables into seconds and devide them.
   *@return time in a adequate format.
   */
  @SuppressWarnings("deprecation")
  public static String divTimes(Time time1, int days) {

    int sumSeconds = 0;
    sumSeconds = time1.getHours() * 3600 + time1.getMinutes() * 60 + time1.getSeconds();
    sumSeconds = sumSeconds / days;
    
    int hours = sumSeconds / 3600;
    int minutes = (sumSeconds % 3600) / 60;
    int seconds = sumSeconds % 60;
    
    return String.format("%02d:%02d:%02d", hours, minutes, seconds);
  }
  
  /**
   * Method used to load the FXML file with data from the database,
   * when the main method is started.
   * "Calculate" button that reloads all the the data in the FXML file from the databse.
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    //Updated code to make sure image loads
    backButton.setImage(new Image("file:res/images/ic_arrow_back_black_48dp_2x.png"));
    
    final String cretariaDataFrom;
    final String cretariaDataTo;

    //INITIALIZE CONTROLLS
    calculateButton.setOnAction(new EventHandler<ActionEvent>() {
    
      @Override
      public void handle(ActionEvent event) {
    
        AggregateData aggData = new AggregateData();
        ObservableList<AggregateData> allAggData;

          try {

            allAggData = aggData.getAllAggregateData(fromCalendar.getValue().toString(),
                                                      toCalendar.getValue().toString());
            
            waiterTable.getItems().setAll(allAggData);

            //INITIALIZE DATA FOR RESTOURANT
            Integer averageOrders = 0;
            Float averageSales = 0f;
            String averageTimeConfirm = "00:00:00";
            String averageTimeDeliver = "00:00:00";
            String averageTimeComplete = "00:00:00";

            int dayDistance = getDifferenceDays(fromCalendar,toCalendar);

              for (AggregateData rawAggData:allAggData) {

                averageOrders += rawAggData.getOrders();
                averageSales += rawAggData.getSales();

                averageTimeConfirm = sumTimes(Time.valueOf(averageTimeConfirm),
                   Time.valueOf(rawAggData.getTimeConfirmed().substring(0,8)));
                
                averageTimeDeliver = sumTimes(Time.valueOf(averageTimeDeliver),
                   Time.valueOf(rawAggData.getTimeDelivered().substring(0,8)));

                averageTimeComplete = sumTimes(Time.valueOf(averageTimeComplete),
                     Time.valueOf(rawAggData.getTimeCompleted().substring(0,8)));

              }
              
              averageOrders = averageOrders / dayDistance;
              averageSales = averageSales / dayDistance;
              averageTimeConfirm = divTimes(Time.valueOf(averageTimeConfirm),dayDistance);
              averageTimeDeliver = divTimes(Time.valueOf(averageTimeDeliver),dayDistance);
              averageTimeComplete = divTimes(Time.valueOf(averageTimeComplete),dayDistance);

              rordersField.setText(averageOrders.toString());
              rsalesField.setText(averageSales.toString().substring(0,4));
              rtconfirmField.setText(averageTimeConfirm);
              rtdeliverField.setText(averageTimeDeliver);
              rtcompleteField.setText(averageTimeComplete);

            } catch (IOException exception) {
              exception.printStackTrace();
            }
      }
    });

    //INITIALIZE DATA FOR WAITER
    wnameColumn.setCellValueFactory(new PropertyValueFactory<AggregateData, String>("waiterName"));
    wordersColumn.setCellValueFactory(new PropertyValueFactory<AggregateData, Integer>("orders"));
    wsalesColumn.setCellValueFactory(new PropertyValueFactory<AggregateData, Float>("sales"));
    
    wtconfirmColumn.setCellValueFactory(new PropertyValueFactory<AggregateData,
        String>("timeConfirmed"));
    
    wtdeliverColumn.setCellValueFactory(new PropertyValueFactory<AggregateData,
        String>("timeDelivered"));
    
    wtcompleteColumn.setCellValueFactory(new PropertyValueFactory<AggregateData,
        String>("timeCompleted"));

    AggregateData aggData = new AggregateData();
    ObservableList<AggregateData> allAggData;
    
    try {
    
      fromCalendar.setValue(localDate());
      toCalendar.setValue(localDate());
      cretariaDataFrom = fromCalendar.getValue().toString();
      cretariaDataTo = toCalendar.getValue().toString();
      allAggData = aggData.getAllAggregateData(cretariaDataFrom, cretariaDataTo);
      waiterTable.getItems().setAll(allAggData);
    
      //INITIALIZE DATA FOR RESTOURANT
      Integer averageOrders = 0;
      Float averageSales = 0f;
      String averageTimeConfirm = "00:00:00";
      String averageTimeDeliver = "00:00:00";
      String averageTimeComplete = "00:00:00";
    
      int dayDistance = getDifferenceDays(fromCalendar,toCalendar);
    
      for (AggregateData rawAggData:allAggData) {
    
        averageOrders += rawAggData.getOrders();
        averageSales += rawAggData.getSales();
    
        averageTimeConfirm = sumTimes(Time.valueOf(averageTimeConfirm),
          Time.valueOf(rawAggData.getTimeConfirmed().substring(0,8)));
        
        averageTimeDeliver = sumTimes(Time.valueOf(averageTimeDeliver),
          Time.valueOf(rawAggData.getTimeDelivered().substring(0,8)));
    
        averageTimeComplete = sumTimes(Time.valueOf(averageTimeComplete),
          Time.valueOf(rawAggData.getTimeCompleted().substring(0,8)));

      }
      
      averageOrders = averageOrders / dayDistance;
      averageSales = averageSales / dayDistance;
      averageTimeConfirm = divTimes(Time.valueOf(averageTimeConfirm),dayDistance);
      averageTimeDeliver = divTimes(Time.valueOf(averageTimeDeliver),dayDistance);
      averageTimeComplete = divTimes(Time.valueOf(averageTimeComplete),dayDistance);
     
      rordersField.setText(averageOrders.toString());
      rsalesField.setText(averageSales.toString());
      rtconfirmField.setText(averageTimeConfirm);
      rtdeliverField.setText(averageTimeDeliver);
      rtcompleteField.setText(averageTimeComplete);
    
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }
  
  //-------Darren Code-----//
  @FXML
  private ImageView backButton;

  private Staff thisManager;

  @FXML
  void navigateBack(MouseEvent event) {
    Navigation.navigateToStaff("/manager/managermainmenu/ManagerMainPage.fxml",
        (Stage) backButton.getScene().getWindow(), thisManager, "Manager Main Menu");
  }

  public void initData(Staff user) {
    this.thisManager = user;
  }

}