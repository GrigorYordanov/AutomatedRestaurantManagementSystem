package staff.menu;

import java.io.IOException;
import java.net.MalformedURLException;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import server.httprequest.HttpRequester;

/**
 * Represent dishes that are stored in the database. It associates the dish with
 * its ingredients, cost, allergies and other characteristics (as shown by the
 * constructors).
 * 
 * @author Mark Abdel Malak
 *
 */

public class Dish {

  private SimpleIntegerProperty id;
  private SimpleStringProperty name;
  private SimpleFloatProperty cost;
  private SimpleStringProperty pictureUrl;
  private SimpleIntegerProperty foodTypeId;
  private SimpleIntegerProperty dietaryReqId;
  private SimpleIntegerProperty sortOrder;
  private SimpleBooleanProperty inStock;
  private SimpleIntegerProperty mealdealId;
  private SimpleStringProperty foodTypeName;
  private SimpleStringProperty dietaryReqName;
  private ObservableList<Ingredient> ingredients;
  private static HttpRequester httpRequester = new HttpRequester();

  /**
   * Construct a Dish without any initial variables initialised at creation.
   * Required to store Dish objects when parsing from database.
   */
  public Dish() {
    id = new SimpleIntegerProperty();
    name = new SimpleStringProperty();
    cost = new SimpleFloatProperty();
    pictureUrl = new SimpleStringProperty();
    foodTypeId = new SimpleIntegerProperty();
    dietaryReqId = new SimpleIntegerProperty();
    sortOrder = new SimpleIntegerProperty();
    inStock = new SimpleBooleanProperty();
    mealdealId = new SimpleIntegerProperty();
    foodTypeName = new SimpleStringProperty();
    dietaryReqName = new SimpleStringProperty();
    ingredients = FXCollections.observableArrayList();
  }
  
  /**
   * Used when the manager wants to create dishes.
   * @param name of the dish.
   * @param cost of the dish.
   * @param pictureUrl of the dish. This MUST start with "http://" (and https is not supported).
   * @param foodTypeName of the dish.
   * @param dietaryReqName of the dish.
   * @param mealdealId of the dish.
   * @throws NumberFormatException if the float can't be parsed.
   * @throws MalformedURLException if the URL in HttpRequester.sendPost invalid.
   * @throws IOException if there is an issue with HttpRequester.
   */
  public Dish(String name, float cost, String pictureUrl, String foodTypeName,
      String dietaryReqName, int mealdealId)
      throws NumberFormatException, MalformedURLException, IOException {

    this.name = new SimpleStringProperty(name);
    this.cost = new SimpleFloatProperty(cost);
    this.pictureUrl = new SimpleStringProperty(pictureUrl);
    this.foodTypeName = new SimpleStringProperty(foodTypeName);
    this.dietaryReqName = new SimpleStringProperty(dietaryReqName);

    this.foodTypeId = new SimpleIntegerProperty(findFoodTypeIdFromFoodTypeName(foodTypeName));
    this.dietaryReqId = new SimpleIntegerProperty(
        findDietaryReqIdFromDietaryReqName(dietaryReqName));

    this.sortOrder = new SimpleIntegerProperty(0);
    this.inStock = new SimpleBooleanProperty(true);

    this.mealdealId = new SimpleIntegerProperty(mealdealId);
    this.ingredients = FXCollections.observableArrayList();

    // If meal deal id is
    String mealDeal = "NULL";
    if (getMealdealId() != 0) {
      mealDeal = Integer.toString(getMealdealId());
    }

    String sql = 
        "INSERT INTO dishes(name, "
        + "cost, picture_url, food_type_id, dietary_req_id, mealdeal_id, in_stock, sort_order) "
        + "VALUES('" + getName() + "', " + getCost() + ", '" + getPictureUrl() + "', "
        + getFoodTypeId() + ", " + getDietaryReqId() + ", " + mealDeal + ", "
        + String.valueOf(isInStock()) + ",28);";

    httpRequester.sendPost("/database/insertRow", sql);

    this.id = new SimpleIntegerProperty(Integer.parseInt(
        httpRequester.sendPost("/database/select", "SELECT MAX(id) FROM dishes;").split(" \n")[0]));

  }

  private static int findFoodTypeIdFromFoodTypeName(String foodTypeName)
      throws MalformedURLException, IOException {
    String sql = "SELECT id FROM food_type where name = '" + foodTypeName + "';";
    return Integer.parseInt(httpRequester.sendPost("/database/select", sql).split(" \n")[0]);
  }

  private static int findDietaryReqIdFromDietaryReqName(String dietaryReqName)
      throws NumberFormatException, MalformedURLException, IOException {
    String sql = "SELECT id FROM dietary_requirements where name ='" + dietaryReqName + "';";
    return Integer.parseInt(httpRequester.sendPost("/database/select", sql).split(" \n")[0]);
  }

  /**
   * Constructor for Dish. Can set all fields from the database this way for
   * example.
   * 
   * @param id
   *          ID of the dish
   * @param name
   *          Name of the dish
   * @param cost
   *          Cost of the dish
   * @param pictureUrl
   *          URL for the picture of the dish
   * @param foodTypeId
   *          ID for the type of food of the dish
   * @param dietaryReqId
   *          ID for the dietary requirements for the dish
   * @param sortOrder
   *          Defines the preferred order to display dishes in the menu
   * @param inStock
   *          Defines whether the dish is in stock or not
   * @param mealdealId
   *          ID for the dish if the dish is a meal deal
   * @param foodTypeName
   *          Type of food of the dish
   * @param dietaryReqName
   *          Dietary requirements for the dish
   */
  public Dish(int id, String name, float cost, String pictureUrl, int foodTypeId, int dietaryReqId,
      int sortOrder, boolean inStock, int mealdealId, String foodTypeName, String dietaryReqName) {
    this.id = new SimpleIntegerProperty(id);
    this.name = new SimpleStringProperty(name);
    this.cost = new SimpleFloatProperty(cost);
    this.pictureUrl = new SimpleStringProperty(pictureUrl);
    this.foodTypeId = new SimpleIntegerProperty(foodTypeId);
    this.dietaryReqId = new SimpleIntegerProperty(dietaryReqId);
    this.sortOrder = new SimpleIntegerProperty(sortOrder);
    this.inStock = new SimpleBooleanProperty(inStock);
    this.mealdealId = new SimpleIntegerProperty(mealdealId);
    this.foodTypeName = new SimpleStringProperty(foodTypeName);
    this.dietaryReqName = new SimpleStringProperty(dietaryReqName);
    this.ingredients = FXCollections.observableArrayList();
  }

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

  public String getPictureUrl() {
    return pictureUrl.get();
  }

  public void setPictureUrl(String pictureUrl) {
    this.pictureUrl.set(pictureUrl);
  }

  public int getFoodTypeId() {
    return foodTypeId.get();
  }

  public void setFoodTypeId(int foodTypeId) {
    this.foodTypeId.set(foodTypeId);
  }

  public int getDietaryReqId() {
    return dietaryReqId.get();
  }

  public void setDietaryReqId(int dietaryReqId) {
    this.dietaryReqId.set(dietaryReqId);
  }

  public int getSortOrder() {
    return sortOrder.get();
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder.set(sortOrder);
  }

  public boolean isInStock() {
    return inStock.get();
  }

  public void setInStock(boolean inStock) {
    this.inStock.set(inStock);
  }

  public int getMealdealId() {
    return mealdealId.get();
  }

  public void setMealdealId(int mealdealId) {
    this.mealdealId.set(mealdealId);
  }

  public String getFoodTypeName() {
    return foodTypeName.get();
  }

  public void setFoodTypeName(String foodTypeName) {
    this.foodTypeName.set(foodTypeName);
  }

  public String getDietaryReqName() {
    return dietaryReqName.get();
  }

  public void setDietaryReqName(String dietaryReqName) {
    this.dietaryReqName.set(dietaryReqName);
  }

  public ObservableList<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(ObservableList<Ingredient> observableList) {
    this.ingredients = observableList;
  }

  /**
   * Returns a list of all of the dishes on the menu in the database.
   * 
   * @return List of all of the dishes on the menu in the database
   * @throws MalformedURLException
   *           In case there is an exception while contacting the server (URL is
   *           malformed)
   * @throws IOException
   *           In case there is an exception while contacting the server
   *           (Connection exception)
   */
  public ObservableList<Dish> getAllDishes() throws MalformedURLException, IOException {

    ObservableList<Dish> menu = FXCollections.observableArrayList();

    String sqlCommand = "SELECT dishes.id, dishes.name, dishes.cost, dishes.picture_url,"
        + " dishes.food_type_id,dishes.dietary_req_id, dishes.sort_order, dishes.in_stock,"
        + " dishes.mealdeal_id, food_type.name AS food_type_name,"
        + " dietary_requirements.name AS dietary_requirements_name" + " FROM dishes"
        + " INNER JOIN food_type ON (dishes.food_type_id = food_type.id)"
        + " INNER JOIN dietary_requirements ON (dishes.dietary_req_id = dietary_requirements.id)"
        + " ORDER BY dishes.sort_order";
    // EACH LINE == one dish object
    String[] dishes = httpRequester.sendPost("/database/select", sqlCommand).split("\n");
    for (String dish : dishes) {
      // split up and pass into a dish object
      String[] individualDish = dish.split(" ");
      // if mealDealId is null then set its id to zero
      if (individualDish[8].equals("null")) {
        individualDish[8] = "0";
      }
      Dish tempDish = new Dish(Integer.parseInt(individualDish[0]), individualDish[1],
          Float.parseFloat(individualDish[2]), individualDish[3],
          Integer.parseInt(individualDish[4]), Integer.parseInt(individualDish[5]),
          Integer.parseInt(individualDish[6]), Boolean.parseBoolean(individualDish[7]),
          Integer.parseInt(individualDish[8]), individualDish[9], individualDish[10]
      // individualDish[11]
      );

      menu.add(tempDish);
    }

    return menu;
  }

  /**
   * Finds the minimum price of a dish or meal deal. 1.6 multiplied by its cost
   * in the database.
   * 
   * @param tempDish
   *          the dish which requires its minimum cost to be calculated.
   * @return the minimum price (sum of ingredients * 1.6)
   * @throws MalformedURLException
   *           if there is an issue with the URL.
   * @throws IOException
   *           if there is an issue with the server.
   */

  public static float findMinimumPrice(Dish tempDish) throws MalformedURLException, IOException {
    // If its NOT a meal deal then it has id 0 since in the db it was null.
    float minimumPrice = 0;
    // if NOT mealdeal
    if (tempDish.getMealdealId() == 0) {
      String sqlGetAllIngredients = "SELECT distinct ingredients_stock.id, ingredients_stock.name, "
          + "ingredients_stock.cost, "
          + "ingredients_stock.metric_id, metric_unit.name AS metric_name, "
          + "ingredients_stock.calories, "
          + "ingredients_stock.allergy_id, ingredients_allergies.name AS allergy_name, "
          + "ingredients_stock.quantity " + "FROM "
          + "ingredients_stock, ingredients_allergies, metric_unit, dishes_ingredients, dishes "
          + "WHERE ingredients_stock.allergy_id = ingredients_allergies.id AND "
          + "ingredients_stock.metric_id = metric_unit.id AND "
          + "dishes_ingredients.dish_id = dishes.id AND "
          + "ingredients_stock.id = dishes_ingredients.ingredient_id and dishes.id = "
          + tempDish.getId() + ";";

      tempDish.setIngredients(ingredientsStringToList(
          httpRequester.sendPost("/database/select", sqlGetAllIngredients)));

      ObservableList<Ingredient> ingredientsOfTempDish = tempDish.getIngredients();

      for (Ingredient ingredient : ingredientsOfTempDish) {
        minimumPrice += ingredient.getCost();
      }
    } else {
      // else its a mealdeal
      String queryForSumOfCostOfDishesInMealDeal = "SELECT sum(cost) from mealdeal_dishes, dishes "
          + "WHERE mealdeal_dishes.dish_id = dishes.id AND " + "mealdeal_dishes.mealdeal_id = "
          + tempDish.getMealdealId() + ";";

      String mealDealCostList = httpRequester.sendPost("/database/select",
          queryForSumOfCostOfDishesInMealDeal);
      minimumPrice = Float.parseFloat(mealDealCostList);
    }
    return minimumPrice *= 1.6;
  }
  
  /**
   * Used to convert a string (from the server/database) into a observable list of ingredients.
   * @param detailsOfIngredient the string thats returned from the server/database.
   * @return list of ingredients.
   */
  public static ObservableList<Ingredient> ingredientsStringToList(String detailsOfIngredient) {
    String[] allIngredients = detailsOfIngredient.split("\n");

    ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
    for (String ingredientLineWithSpaces : allIngredients) {
      String[] singleIngredient = ingredientLineWithSpaces.split(" ");
      Ingredient ingredient = new Ingredient();
      ingredient.setId(Integer.parseInt(singleIngredient[0]));
      ingredient.setName(singleIngredient[1]);
      ingredient.setCost(Float.parseFloat(singleIngredient[2]));
      ingredient.setMetricId(Integer.parseInt(singleIngredient[3]));
      ingredient.setMetricName(singleIngredient[4]);
      ingredient.setCalories(Integer.parseInt(singleIngredient[5]));
      ingredient.setAllergyId(Integer.parseInt(singleIngredient[6]));
      ingredient.setAllergyName(singleIngredient[7]);
      ingredient.setQuantity(Float.parseFloat(singleIngredient[8]));
      ingredientList.add(ingredient);
    }
    return ingredientList;
  }
}