package server.database.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import server.database.service.DatabaseService;

/**
 * Server directs all /database requests to the database.
 * Database resource contains all the SQL methods for the database. All database queries should (and
 * must) use this class.
 * @author Mark Abdel Malak
 * 
 */

@Path("/database")
public class DatabaseResource {
  DatabaseService databaseService = DatabaseService.getInstance();
  
  /**
   *  Used to delete a row from the database.
   * @param deleteStatement the row which needs to be deleted.
   * @return "Deleted row" if the delete was successful.
   */
  @POST
  @Path("/deleteRow")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public String deleteRow(String deleteStatement) {
    return databaseService.deleteRow(deleteStatement);
  }
  
  /**
   * Used to perform a select on the SQL database.
   * @param selectStatement the select statement.
   * @return the rows selected.
   */
  @POST
  @Path("/select")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public String select(String selectStatement) {
    return databaseService.select(selectStatement);
  }
  
  /**
   * Creates a table in the database. 
   * @param tableDescription the schema of the table which will be created.
   * @return the schema of the table.
   */
  @POST
  @Path("/createtable")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  public String createTable(String tableDescription) {
    databaseService.createTable(tableDescription);
    return tableDescription;
  }
  
  /**
   * Drops a table from the database.
   * @param tableName name of table which will be dropped.
   * @return "dropped" tableName. 
   */
  @DELETE
  @Path("/{tableName}")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.TEXT_PLAIN)
  public String dropTable(@PathParam("tableName")String tableName) {
    databaseService.dropTable(tableName);
    return "dropped " + tableName;
  }
  
  /**
   * To insert a row into the database.
   * @param statement the row which will be inserted.
   * @return whether the insert was successful.
   */
  @POST
  @Path("/insertRow")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public String insertRow(String statement) {
    return databaseService.insertRow(statement);
  }
  
  /**
   * Update a row in the database.
   * @param statement SQL update statement.
   * @return if the row was successfully updated.
   */
  @POST
  @Path("/updateRow")
  @Consumes(MediaType.TEXT_PLAIN)
  @Produces(MediaType.APPLICATION_JSON)
  public String updateRow(String statement) {
    return databaseService.updateRow(statement);
  }
  
  /**Update price in the database.
   * @param input which row will be updated.
   */
  @POST
  @Path("/updatePrice")
  @Consumes(MediaType.TEXT_PLAIN)
  public void updatePrice(String input) {
    String[] array = input.split(" ");
    databaseService.updatePrice(array[0], Integer.parseInt(array[1]), Float.parseFloat(array[2]));
  }
}
