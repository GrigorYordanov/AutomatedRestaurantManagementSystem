package server.database.service;

//Code altered from CS2855 By Iddo Tzameret and Claudia Chirita; DB course 2015-16

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseService uses JDBC to connect to the database. Due to the restrictions on the 
 * number of connections that can be made to the database (20) 
 * this class is a singleton to avoid unnecessary connections being made (which did lead to the
 *  database not responding to queries).
 * @author Mark Abdel Malak
 *
 */
public final class DatabaseService {

  /**
   * The instance of DatabaseService. This allows for only one instance of
   * DatabaseService, making this class a singleton.
   * @author James Harris
   */
  private static volatile DatabaseService instance = null;

  private static Connection connection = null;

  /**
   * Constructor for this class <code>DatabaseService</code>. This constructor
   * is private, so to get an instance of this class, an external class is
   * forced to use the <code>getInstance()</code> method.
   */
  private DatabaseService() {
    connectToDatabase();
  }

  /**
   * Gets the instance of <code>DatabaseService</code>. If DatabaseService
   * hasn't been initialised yet, the instance of this class is initialised.
   * 
   * @return The instance of the DatabaseService class
   * @author James Harris
   */
  public static synchronized DatabaseService getInstance() {
    if (instance == null) {
      instance = new DatabaseService();
    }
    return instance;
  }

  /**
   * Creates a connection to the PSQL with the given credentials.
   * 
   * @return a connection which is required by JDBC methods in order to perform
   *         queries.
   */
  public Connection connectToDatabase() {
    System.out.println("-------- PostgreSQL " + "JDBC Connection Testing ------------");

    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException exception) {
      System.out.println("Where is your PostgreSQL JDBC Driver? Include in your library path!");
      exception.printStackTrace();
    }

    System.out.println("PostgreSQL JDBC Driver Registered!");
    String user = "wrjdzkknwbwrgy";
    String password = "c088b08d8dd44a728068086ae26a1007a1c24c5db4fb6f583cd092aec7a8093c";
    String databaseName = "d5krt20tjkhn6b";
    String port = ":5432";
    String hostName = "ec2-54-75-235-2.eu-west-1.compute.amazonaws.com";

    String database = "jdbc:postgresql://" + hostName + port + "/" + databaseName
        + "?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory";

    try {
      connection = DriverManager.getConnection(database, user, password);
    } catch (SQLException exception) {
      System.out.println("Connection Failed! Check output console");
      exception.printStackTrace();
    }

    if (connection != null) {
      System.out.println("You made it, take control your database now!");
    } else {
      System.out.println("Failed to make connection!");
      return null;
    }
    return connection;
  }

  /**
   * Performs a select statement on the PSQL database.
   * 
   * @param query
   *          is the select statement that is required.
   * @return which contains the result to the select statement.
   */
  private ResultSet executeSelect(String query) {
    Statement st = null;
    try {
      st = connection.createStatement();
    } catch (SQLException exception) {
      exception.printStackTrace();
      return null;
    }
    ResultSet rs = null;
    try {
      rs = st.executeQuery(query);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return null;
    }
    return rs;
  }

  /**
   * Drops a table from the database.
   * 
   * @param table
   *          is the table which will be dropped.
   */
  public void dropTable(String table) {
    Statement st = null;
    try {
      st = connection.createStatement();
      st.execute("DROP TABLE " + table);
      st.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Creates a table in the PSQL database.
   * 
   * @param tableDescription
   *          is the schema of the table being added.
   */
  public void createTable(String tableDescription) {
    Statement st = null;
    try {
      st = connection.createStatement();
      st.execute("CREATE TABLE " + tableDescription);
      st.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  /**
   * Fetches the string thats stored in the ResultSet.
   * @author James Harris
   * @param resultset
   *          is passed from executeSelect.
   * @return a string with the result of the select statement.
   */
  private String getResult(ResultSet resultset) {
    ResultSet rs = resultset;
    StringBuilder buf = new StringBuilder();
    try {
      int columnCount = rs.getMetaData().getColumnCount();
      while (rs.next()) {
        for (int i = 1; i <= columnCount; i++) {
          buf.append(rs.getString(i) + " ");
        }
        buf.append("\n");
      }
      rs.close();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
    return buf.toString();
  }

  /**
   * A wrapper for select.
   * 
   * @param query
   *          is the select statement.
   * @return the result is returned.
   */
  public String select(String query) {
    return getResult(executeSelect(query));
  }

  /**
   * Deletes a row from a table. It must contain the two keywords FROM and
   * WHERE.
   * 
   * @param row
   *          the row(s) which wants to be deleted.
   * @return "Deleted row" if the row was deleted.
   */

  public String deleteRow(String row) {
    Statement statement;
    try {
      // Where is required so that you don't delete a whole table ( only a row
      // can be deleted).
      if (!(row.toLowerCase().contains("from") && row.toLowerCase().contains("where"))) {
        return "Invalid row deletion syntax";
      }
      statement = connection.createStatement();
      statement.executeUpdate(row);
      statement.close();
      return "Deleted row";
    } catch (SQLException exception) {
      exception.printStackTrace();
      return "Failure deleting row";
    }
  }

  /**
   * Inserts a row into a table.
   * 
   * @param row
   *          that is being inserted into the table.
   * @return whether the row was successfully inserted.
   */
  public String insertRow(String row) {
    Statement statement;
    try {
      if (!row.toLowerCase().contains("insert into ")) {
        return "Invalid row insertion syntax.";
      }
      statement = connection.createStatement();
      statement.executeUpdate(row);
      return "Row inserted";
    } catch (SQLException exception) {
      exception.printStackTrace();
      return "Issue inserting row, Exception thrown!";
    }
  }
  
  /**
   * Update a row that is in the database.
   * @param row the update statement which will update the specific row.
   * @return whether or not the update was successful.
   */
  public String updateRow(String row) {
    Statement statement;
    if (!row.toLowerCase().contains("update")) {
      return "Invalid row update syntax.";
    }
    try {
      statement = connection.createStatement();
      statement.executeUpdate(row);
    } catch (SQLException exception) {
      exception.printStackTrace();
      return "Issue updating row, Exception thrown!";
    }

    return "Row updated";
  }
  
  /**
   * Used to update the price of a specific item in a table.
   * @param table the table which will be updated.
   * @param id of the item in the table which will be updated.
   * @param cost the new cost of the item.
   */
  
  //  Code used from https://docs.oracle.com/javase/tutorial/jdbc/basics/transactions.html
  public void updatePrice(String table, int id, float cost) {
    PreparedStatement updateRow = null;

    String updateString = "update " + table + " set cost = ? where id = ?";

    try {
      connection.setAutoCommit(false);
      updateRow = connection.prepareStatement(updateString);
      updateRow.setFloat(1, cost);
      updateRow.setInt(2, id);
      updateRow.executeUpdate();
      connection.commit();
    } catch (SQLException exception) {
      exception.printStackTrace();
      if (connection != null) {
        try {
          System.err.print("Update is being rolled back");
          connection.rollback();
        } catch (SQLException excep) {
          exception.printStackTrace();
        }
      }
    } finally {
      try {
        if (updateRow != null) {
          updateRow.close();
        }
        connection.setAutoCommit(true);
      } catch (SQLException exception) {
        exception.printStackTrace();
      }
    }
  }

}
