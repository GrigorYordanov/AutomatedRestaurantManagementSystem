
/**
  * The package 'viewmenu' contains the controller, main and
  * object classes that are used to pollute the FXML table with
  * data, so the customers can view the menu of the restaurant.
  */

package customer.viewmenu;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import server.httprequest.HttpRequester;

/**
 * The 'Staff' class is used to create staff objects 'singleIngredient' with data from the database.
 * @author Svetoslav Mihovski
 */
public class ViewMenuDishes {

  //Private fields of the class used to create the object.
  private SimpleIntegerProperty id;
  private SimpleStringProperty imageName;
  private ImageView image;
  private SimpleStringProperty name;
  private SimpleStringProperty ingredients;
  private SimpleFloatProperty calories;
  private SimpleStringProperty foodType;
  private SimpleStringProperty allergyInfo;
  private SimpleStringProperty dietaryReq;
  private SimpleFloatProperty price;
  
  /**
   * Initialise the dish fields. This is to be used in conjunction with all the
   * getters of ViewMenuDishes to make it more readable.
   */ 
  public ViewMenuDishes() {

    this.id = new SimpleIntegerProperty();
    this.imageName = new SimpleStringProperty();
    this.image = new ImageView();
    this.name = new SimpleStringProperty();
    this.ingredients = new SimpleStringProperty();
    this.calories = new SimpleFloatProperty();
    this.foodType = new SimpleStringProperty();
    this.allergyInfo = new SimpleStringProperty();
    this.dietaryReq = new SimpleStringProperty();
    this.price = new SimpleFloatProperty();
  }
  
  /**
   * Initialise all fields of an dish.
   * @param image of the dish.
   * @param name of the dish.
   * @param ingredients of the dish. 
   * @param calories its type, whether its in KG or Litres or quantity.
   * @param allergyInfo for the food.
   * @param dietaryReq of the food.
   * @param price of dish.
   */
  public ViewMenuDishes(int id, String imageName, ImageView image, String name, String ingredients,
         float calories, String foodType, String allergyInfo, String dietaryReq, float price) {

    // Assign fields
    this.id = new SimpleIntegerProperty(id);
    this.imageName = new SimpleStringProperty(imageName);
    this.image = image;
    this.name = new SimpleStringProperty(name);
    this.ingredients = new SimpleStringProperty(ingredients);
    this.calories = new SimpleFloatProperty(calories);
    this.foodType = new SimpleStringProperty(foodType);
    this.allergyInfo = new SimpleStringProperty(allergyInfo);
    this.dietaryReq = new SimpleStringProperty(dietaryReq);
    this.price = new SimpleFloatProperty(price);
  }
  
  /**
   * Returns a list of all of the specific menu data from the database.
   * 
   * @return a list of information about a the menu of the restaurant.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<ViewMenuDishes> getAllIngredients() throws MalformedURLException,
      IOException {

    HttpRequester httpRequester = new HttpRequester();
    List<ViewMenuDishes> tempMenu = new ArrayList<ViewMenuDishes>();
    ObservableList<ViewMenuDishes> viewMenu = FXCollections.observableArrayList();

    String sqlCommand = "SELECT dishes.picture_url, dishes.name,"
        + " ingredients_stock.name AS ingredients,"
        + " ( ingredients_stock.calories ) *( dishes_ingredients.ingredient_qty ) AS calories,"
        + " food_type.name AS food_type,ingredients_allergies.name AS allergies,"
        + " dietary_requirements.name AS dietary_req, dishes.cost, dishes.id"
        + " FROM dishes"
        + " INNER JOIN dietary_requirements"
        + " ON dishes.dietary_req_id = dietary_requirements.id"
        + " INNER JOIN food_type"
        + " ON dishes.food_type_id = food_type.id"
        + " INNER JOIN dishes_ingredients"
        + " ON dishes.id = dishes_ingredients.dish_id"
        + " INNER JOIN ingredients_stock"
        + " ON dishes_ingredients.ingredient_id = ingredients_stock.id"
        + " INNER JOIN ingredients_allergies"
        + " ON ingredients_stock.allergy_id = ingredients_allergies.id"
        + " WHERE in_stock = true"
        + " ORDER BY dishes.sort_order ASC;";
      
    String[] ingredients = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    for (String ingredient : ingredients) {
    
      String[] singleIngredient = ingredient.split(" ");
      
      ImageView picture = null;
      ViewMenuDishes tempIngredient = new ViewMenuDishes(
                  
                  Integer.parseInt(singleIngredient[8]),
                  singleIngredient[0],
                  picture,
                  singleIngredient[1],
                  singleIngredient[2],
                  Float.parseFloat(singleIngredient[3]),
                  singleIngredient[4],
                  singleIngredient[5],
                  singleIngredient[6],
                  Float.parseFloat(singleIngredient[7])
              );
      tempMenu.add(tempIngredient);
    }
    
    System.out.println(tempMenu.toString());
    
    int id = -1;
    String pictureUrl = "";
    ImageView picture = null;
    String dishName = "";
    String dishIngredients = "";
    float dishCalories = 0;
    String foodType = "";
    String allergyInfo = "";
    String dietaryReq = "";
    float cost = 0;
    boolean endRow = false;
    boolean newSingleRow = false;
    
    for (ViewMenuDishes dish : tempMenu) {
      if (dish.getName().equals(dishName)) { //add to dish
    
        dishIngredients = dishIngredients + dish.getIngredients() + ";";
        dishCalories = dishCalories + dish.getCalories();
        
        if (!allergyInfo.contains(dish.getAllergyInfo())
              && (!dish.getAllergyInfo().equals("Non-allergenic"))) {
        
          allergyInfo = allergyInfo  + dish.getAllergyInfo() + ";";
          allergyInfo = allergyInfo.replace("Non-allergenic", "");
          allergyInfo = removeFirstComaChar(allergyInfo);
        }
        endRow = true;
        newSingleRow = false;

      } else { //first row for dish
   
        if (endRow || newSingleRow) {
        
          viewMenu.add(new ViewMenuDishes(id, pictureUrl, picture, dishName,
              removeLastChar(dishIngredients), dishCalories,
              foodType, removeLastChar(allergyInfo), dietaryReq, cost));
          endRow = false;
          newSingleRow = true;
        }
        
        pictureUrl = dish.getImageName();
        String pictureUrlPrefix = "";
        if (!pictureUrl.startsWith("http://")) {
          pictureUrlPrefix = "file:res/images/dishes/";
        }
        picture = new ImageView(new Image(pictureUrlPrefix + pictureUrl 
                                                       + "",100,100,true,true));
        
        id = dish.getId();
        dishName = dish.getName();
        dishName = dish.getName();
        dishIngredients = dish.getIngredients() + ";";
        dishCalories = dish.getCalories();
        foodType = dish.getFoodType();
        allergyInfo = dish.getAllergyInfo() + ";";
        dietaryReq = dish.getDietaryReq();
        cost = dish.getPrice();
      }
    }
    viewMenu.add(new ViewMenuDishes(id, pictureUrl, picture, dishName,
        removeLastChar(dishIngredients), dishCalories, foodType,
        removeLastChar(allergyInfo), dietaryReq, cost)); //add last row
    
   
    return viewMenu;
  }    

  //Set of getters and setters for the private fields.  
  
  public int getId() {
    return id.get();
  }
  
  public void setId(int id) {
    this.id.set(id);
  }
  
  public String getImageName() {
    return imageName.get();
  }
  
  public void setImageName(String imageName) {
    this.imageName.set(imageName);
  }
  
  public void setImage(ImageView value) {
    image = value;
  }

  public ImageView getImage() {
    return image;
  }

  public String getName() {
    return name.get();
  }
  
  public void setName(String name) {
    this.name.set(name);
  }
  
  public String getIngredients() {
    return ingredients.get();
  }
  
  public void setIngredients(String ingredients) {
    this.ingredients.set(ingredients);
  }
  
  public float getCalories() {
    return calories.get();
  }

  public void setCalories(float calories) {
    this.calories.set(calories);
  }
  
  public String getFoodType() {
    return foodType.get();
  }

  public void setFoodType(String foodType) {
    this.foodType.set(foodType);
  }
  
  public String getAllergyInfo() {
    return allergyInfo.get();
  }
  
  public void setAllergyInfo(String allergyInfo) {
    this.allergyInfo.set(allergyInfo);
  }
  
  public String getDietaryReq() {
    return dietaryReq.get();
  }
  
  public void setDietaryReq(String dietaryReq) {
    this.dietaryReq.set(dietaryReq);
  }
  
  public float getPrice() {
    return price.get();
  }
  
  public void setPrice(float price) {
    this.price.set(price);
  }
  
  @Override
  public String toString() {
    return getId() + " " + getImage() + " " + getName() + " " + getIngredients() + " "
      + getCalories() + " " + getAllergyInfo() + " " + getDietaryReq() + " " + getPrice();
  }
  
  private static String removeLastChar(String str) {
    return str.substring(0,str.length() - 1);
  }
  
  private static String removeFirstComaChar(String str) {
    if (str.substring(0,1).equals(";")) {
      str = str.replaceFirst(";", "");
    }
    return str;
  }
  
}