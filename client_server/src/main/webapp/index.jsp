<html>
<body>
    <h1>Cafe System Project - Team 5</h1>
    <p>Current API</p>
    <table border="1">
    	<tr>
    		<th>Path</th>
    		<th>Function</th>
    		<th>Parameters</th>
    		<th>Return</th>
    	</tr>
    	<tr>
    		<td>/MenuItem/GetDishes</td>
    		<td>Gets all the Dishes in the Database</td>
    		<td>NONE</td>
    		<td>Returns STRING saying getting all Dishes</td>
    	</tr>
    	<tr>
    		<td>/MenuItem/GetDish/{dishID}</td>
    		<td>Gets the Dish specified by dishID from Database</td>
    		<td>dishID - int - The unique Identifier for the Dish</td>
    		<td>Returns STRING saying getting the specific dish by ID</td>
    	</tr>
    	<tr>
    		<td>/MenuItem/RemoveDish/{dishID}</td>
    		<td>Removes the specific dish from the Database</td>
    		<td>dishID - int - The unique Identifier for the Dish</td>
    		<td>Returns STRING saying removing dish with ID</td>
    	</tr>
    	<tr>
    		<td>/MenuItem/AddDish/{name}/{ingredientListID}/{price}</td>
    		<td>Add dish with these attribute to the Database</td>
    		<td>name - String - The name of the dish <br><br> ingredientListID - String - List of ingredient ID using format ID,ID,...,ID <br><br> price - float - The price of the dish</td>
    		<td>Returns STRING saying the name of the Dish, what Ingredient ID and the price</td>
    	</tr>
    </table>
</body>
</html>