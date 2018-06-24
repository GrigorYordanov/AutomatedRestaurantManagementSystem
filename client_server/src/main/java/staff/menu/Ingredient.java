package staff.menu;

import java.io.IOException;
import java.net.MalformedURLException;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import manager.editmenu.AddIngredientController;

import org.apache.commons.lang3.StringUtils;
import server.httprequest.HttpRequester;

/**
 * The 'Ingredient' class is used to create ingredient objects 'singleIngredient' ,
 * with data from the database.
 * @author Svetoslav Mihovski
 */
public class Ingredient {

  //Private fields of the class used to create the object.
  private SimpleIntegerProperty id;
  private SimpleStringProperty name;
  private SimpleFloatProperty cost;
  private SimpleIntegerProperty metricId;
  private SimpleStringProperty metricName;
  private SimpleIntegerProperty calories;
  private SimpleIntegerProperty allergyId;
  private SimpleStringProperty allergyName;
  private SimpleFloatProperty quantity;
  private static HttpRequester httpRequester = new HttpRequester();

  /**
   * Initialise the ingredients fields. This is to be used in conjunction with
   * all the getters of Ingredient to make it more readable.
   */
  public Ingredient() {
    this.id = new SimpleIntegerProperty();
    this.name = new SimpleStringProperty();
    this.cost = new SimpleFloatProperty();
    this.metricId = new SimpleIntegerProperty();
    this.metricName = new SimpleStringProperty();
    this.calories = new SimpleIntegerProperty();
    this.allergyId = new SimpleIntegerProperty();
    this.allergyName = new SimpleStringProperty();
    this.quantity = new SimpleFloatProperty();
  }
  
  /**
   * Constructor of 'Ingredient' class is used to initialize the fields in the database such as:
   * @param id - id of an ingredient.
   * @param name - name of an ingredient.
   * @param qty - quantity of an ingredient.
   */
  
  public Ingredient(int id, String name, float qty) {

    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.quantity = new SimpleFloatProperty(qty);

  }
  
  /**
   * Initialise all fields of an ingredient.
   * 
   * @param name
   *          of the ingredient.
   * @param cost
   *          of the ingredient.
   * @param metricName
   *          is the name of the metric id.
   * @param calories
   *          of the food.
   * @param allergyName
   *          name of allergy id.
   * @param quantity
   *          amount of stock.
  * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws NumberFormatException
   *           In a case a number is converted to String.
   */
  public Ingredient(String name, float cost, String metricName, int calories, String allergyName,
      float quantity) throws NumberFormatException, MalformedURLException, IOException {

    // Assign fields
    this.name = new SimpleStringProperty(name);
    this.cost = new SimpleFloatProperty(cost);
    this.metricName = new SimpleStringProperty(metricName);
    this.metricId = new SimpleIntegerProperty(findMetricIdFromMetricName(metricName));
    this.calories = new SimpleIntegerProperty(calories);
    this.allergyName = new SimpleStringProperty(allergyName);
    this.allergyId = new SimpleIntegerProperty(findAllergyIdFromAllergyName(allergyName));
    this.quantity = new SimpleFloatProperty(quantity);

    // Insert ingredient into database
    String sql = 
        "INSERT INTO ingredients_stock(name, cost, metric_id, calories, allergy_id, quantity) "
        + "VALUES('"
        + getName() + "', " + Float.toString(getCost()) + ", " + getMetricId() + ", "
        + getCalories() + ", " + getAllergyId() + ", " + getQuantity() 
        + ")";

    httpRequester.sendPost("/database/insertRow", sql);
    //get id of ingredient
    this.id = new SimpleIntegerProperty(Integer.parseInt(httpRequester
        .sendPost("/database/select", "SELECT MAX(id) FROM ingredients_stock;").split(" \n")[0]));
  }

  private static int findAllergyIdFromAllergyName(String allergyName)
      throws NumberFormatException, MalformedURLException, IOException {
    String sql = "SELECT id FROM ingredients_allergies WHERE name = '"
        + StringUtils.capitalize(allergyName) + "';";
    return Integer.parseInt(AddIngredientController.selectFromDb(sql)[0]);
  }

  private static int findMetricIdFromMetricName(String metricName)
      throws MalformedURLException, IOException {

    String sql = "SELECT id FROM metric_unit WHERE name = '" + metricName + "';";
    return Integer.parseInt(AddIngredientController.selectFromDb(sql)[0]);
  }

  /**
   * Initialise all fields of an ingredient.
   * 
   * @param id
   *          of the ingredient.
   * @param name
   *          of the ingredient.
   * @param cost
   *          of the ingredient.
   * @param metricId
   *          its type, whether its in KG or Litres or quantity.
   * @param metricName
   *          is the name of the metric id.
   * @param calories
   *          of the food.
   * @param allergyId
   *          any possible allergies.
   * @param allergyName
   *          name of allergy id.
   * @param quantity
   *          amount of stock.
   */
  public Ingredient(int id, String name, float cost, int metricId, String metricName, int calories,
      int allergyId, String allergyName, float quantity) {

    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.cost = new SimpleFloatProperty(cost);
    this.metricId = new SimpleIntegerProperty(metricId);
    this.metricName = new SimpleStringProperty(metricName);
    this.calories = new SimpleIntegerProperty(calories);
    this.allergyId = new SimpleIntegerProperty(allergyId);
    this.allergyName = new SimpleStringProperty(allergyName);
    this.quantity = new SimpleFloatProperty(quantity);

  }

  /**
   * Returns a list of all of the specific all ingredient data from the database.
   * 
   * @return a list of information about an ingredient.
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<Ingredient> getAllIngredients() throws MalformedURLException, IOException {
    HttpRequester httpRequester = new HttpRequester();
    ObservableList<Ingredient> stock = FXCollections.observableArrayList();

    String sqlCommand = "SELECT ingredients_stock.id, ingredients_stock.name,"
        + " ingredients_stock.cost,"
        + " ingredients_stock.metric_id, metric_unit.name AS metric_name,"
        + " ingredients_stock.calories,"
        + " ingredients_stock.allergy_id, ingredients_allergies.name AS allergy_name,"
        + " ingredients_stock.quantity" + " FROM ingredients_stock"
        + " INNER JOIN ingredients_allergies"
        + " ON (ingredients_stock.allergy_id = ingredients_allergies.id)"
        + " INNER JOIN metric_unit" + " ON (ingredients_stock.metric_id = metric_unit.id);";

    String[] ingredients = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    for (String ingredient : ingredients) {

      String[] singleIngredient = ingredient.split(" ");

      if (singleIngredient[8].equals("null")) {
        singleIngredient[8] = "0";
      }

      Ingredient tempIngredient = new Ingredient(Integer.parseInt(singleIngredient[0]),
          singleIngredient[1], Float.parseFloat(singleIngredient[2]),
          Integer.parseInt(singleIngredient[3]), singleIngredient[4],
          Integer.parseInt(singleIngredient[5]), Integer.parseInt(singleIngredient[6]),
          singleIngredient[7], Float.parseFloat(singleIngredient[8]));
      stock.add(tempIngredient);
    }
    return stock;
  }

  /**
   * Creates ObservableList of all ingredients within a specific Dish.
   * 
   * @param dishId The ID of the Dish whose ingredients are to be retrieved
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public ObservableList<Ingredient> getIngredientsById(int dishId) 
      throws MalformedURLException, IOException {

    ObservableList<Ingredient> viewIngredients = FXCollections.observableArrayList();

    String sqlCommand = "SELECT ingredients_stock.id, ingredients_stock.name,"
        + " dishes_ingredients.ingredient_qty"
        + " FROM dishes_ingredients"
        + " INNER JOIN ingredients_stock"
        + " ON dishes_ingredients.ingredient_id = ingredients_stock.id"
        + " WHERE dishes_ingredients.dish_id = '" + dishId + "'";

    String[] ingredients = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    for (String ingredient : ingredients) {

      String[] singleIngredient = ingredient.split(" ");

      Ingredient tempIngredient = new Ingredient(
          Integer.parseInt(singleIngredient[0]), 
          singleIngredient[1],
          Float.parseFloat(singleIngredient[2]));

      viewIngredients.add(tempIngredient);
    }

    return viewIngredients;
  }

  /**
   * Adds the ingredient to a specific Dish.
   * 
   * @param dishId The ID of the Dish to which the ingredient is added
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */ 
  public void addIngredientToDish(int dishId) throws MalformedURLException, IOException {

    String sqlCommand = null;

    sqlCommand = "INSERT INTO dishes_ingredients (dish_id, ingredient_id, ingredient_qty)"
        + " VALUES ('" + dishId + "', '" + this.getId() + "', '0')";

    httpRequester.sendPost("/database/select", sqlCommand).split("\n");

  }
  
  /**
   * Removes the ingredient to a specific Dish.
   * 
   * @param dishId The ID of the Dish from which the ingredient is removed
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed).
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception).
   */
  public void removeIngredientFromDish(int dishId) throws MalformedURLException, IOException {

    String sqlCommand = null;

    sqlCommand = "DELETE FROM dishes_ingredients"
        + " WHERE dish_id = '" + dishId + "' AND ingredient_id = '" + this.getId() + "'";

    httpRequester.sendPost("/database/select", sqlCommand).split("\n");

  }
  
  //Set of getters and setters for the private fields.
  
  public int getId() {
    return id.get();
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public String getName() {
    return name.get();
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public float getCost() {
    return cost.get();
  }

  public void setCost(float cost) {
    this.cost.set(cost);
  }

  public int getMetricId() {
    return metricId.get();
  }

  public void setMetricId(int metricId) {
    this.metricId.set(metricId);
  }

  public String getMetricName() {
    return metricName.get();
  }

  public void setMetricName(String metricName) {
    this.metricName.set(metricName);
  }

  public int getCalories() {
    return calories.get();
  }

  public void setCalories(int calories) {
    this.calories.set(calories);
  }

  public int getAllergyId() {
    return allergyId.get();
  }

  public void setAllergyId(int allergyId) {
    this.allergyId.set(allergyId);
  }

  public String getAllergyName() {
    return allergyName.get();
  }

  public void setAllergyName(String allergyName) {
    this.allergyName.set(allergyName);
  }

  public float getQuantity() {
    return quantity.get();
  }

  public void setQuantity(float quantity) {
    this.quantity.set(quantity);
  }

  @Override
  public String toString() {
    return getId() + " " + getName() + " " + getMetricId() + " " + getMetricName() + " "
        + getCalories() + " " + getAllergyId() + " " + getAllergyName() + " " + getQuantity();
  }
}