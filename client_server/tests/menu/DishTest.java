package menu;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import staff.menu.Dish;

public class DishTest {
  private Dish dish;

  @Before
  public void setUp() throws Exception {
    dish = new Dish();
  }

  @Test
  public void testGetId() {
    dish.setId(12);
    assertThat("Test 1: set and get id", dish.getId(), is(12));
  }

  @Test
  public void testGetName() {
    dish.setName("apple");
    assertThat("Test 2: set name and get name", dish.getName(), is("apple"));
  }

  @Test
  public void testGetCost() {
    dish.setCost(12.0f);
    assertThat("Test 3: set cost and get cost", dish.getCost(), is(12.0f));
  }


  @Test
  public void testGetPictureUrl() {
    dish.setPictureUrl("www.google.com/images/apple");
    assertThat("Test 4: set url and get", dish.getPictureUrl(), is("www.google.com/images/apple"));
  }

  @Test
  public void testGetFoodTypeId() {
    dish.setFoodTypeId(122);
    assertThat("Test 5: set food type id and get", dish.getFoodTypeId(), is(122));
  }

  @Test
  public void testGetDietaryReqId() {
    dish.setDietaryReqId(1);
    assertThat("Test 6: set dietary req and get", dish.getDietaryReqId(), is(1));
  }

  @Test
  public void testGetSortOrder() {
    dish.setSortOrder(1);
    assertThat("Test 7: set sort order and get", dish.getSortOrder(), is(1));
  }

  @Test
  public void testIsInStock() {
    dish.setInStock(true);
    assertThat("Test 8: set stock and get", dish.isInStock(), is(true));
  }

  @Test
  public void testGetMealdealId() {
    dish.setMealdealId(111);
    assertThat("Test 9: set dish meal deal id and get", dish.getMealdealId(), is(111));
  }

  @Test
  public void testGetFoodTypeName() {
    dish.setFoodTypeName("poultry");
    assertThat("Test 10: set food type name and get", dish.getFoodTypeName(), is("poultry"));
  }

  @Test
  public void testGetDietaryReqName() {
    dish.setDietaryReqName("lactose intolerant");
    assertThat("Test 11: set diet restriction and get", 
        dish.getDietaryReqName(), is("lactose intolerant"));
  }

  /*  
  @Test
  public void testGetIngredients() {
    ObservableList<realtimedata.Ingredient> ingredientList = FXCollections.observableArrayList();
    ingredientList.add(new Ingredient(12, "cheese", 12.00, 1, 1, 120, 1, "lactose", 1000));
    dish.setIngredients(ingredientList);
    assertThat("Test 12: set ingredients and set", dish.getIngredients())
    
  }

  @Test
  public void testGetAllDishes() {
    fail("Not yet implemented");
  }

  @Test
  public void testFindMinimumPrice() {
    fail("Not yet implemented");
  }

  @Test
  public void testIngredientsStringToList() {
    fail("Not yet implemented");
  }*/

}
