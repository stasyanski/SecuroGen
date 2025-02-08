package sg.database;

/**
 * JavaSQL Library
 */

import java.sql.*;

/**
 * The DatabaseTable class aims to be a DAO (Data Access Object) pattern to
 * provide a way to access a database in an abstract way
 *
 * [in code reference]
 * GeeksForGeeks. (2017). Data Access Object Pattern. [online] Available from:
 * @see https://www.geeksforgeeks.org/data-access-object-pattern/
 *
 * The class provides common database operations for a specific table
 * and can be called and constructed into any class
 */
public class DatabaseTable {
    private final Connection connection;
    private final String tableName;
    private final String primaryKey;

    /**
     * Constructs a DatabaseTable object with the specified database connection,
     * table name, and the primary key of the table.
     * @param connection the database connection
     * @param tableName  the name of the table
     * @param primaryKey the primary key of the table
     */
    public DatabaseTable(Connection connection, String tableName, String primaryKey) {
        this.connection = connection;
        this.tableName = tableName;
        this.primaryKey = primaryKey;
    }

    /**
     * This method inserts a new row into a table depending on the tableName
     * it takes in an associative array of field - > value
     * and constructs a sql insert statement
     * inserts values and fields will have to be casted
     * to string before insert
     * @param fieldsAndValues an array of field names and values
     */
    public void insert(String[] fieldsAndValues) {
        try {
            // holds the field names
            String fields = "";
            // holds the values
            String values = "";

            // loops through the fields and values to append each field and value to its string, with commas for separation
            for (int i = 0; i < fieldsAndValues.length; i += 2) {
                if (i > 0) {
                    fields += ", ";
                    values += ", ";
                }
                fields += fieldsAndValues[i]; // append the field name
                values += "?"; // append the value placeholder
            }

            // build the sql insert statement
            String sql = "INSERT INTO " + this.tableName + " (" + fields + ") VALUES (" + values + ")";

            // set the values with a for loop, using a prepared statement can help avoid sql injection
            try (PreparedStatement stmt = this.connection.prepareStatement(sql)) {
                for (int i = 1; i < fieldsAndValues.length; i += 2) {
                    stmt.setString((i / 2) + 1, fieldsAndValues[i]); // set the value for each placeholder
                }
                stmt.executeUpdate(); // execute the update
            }
        } catch (SQLException e) {
            System.err.println("Error during insert " + e.getMessage());
        }
    }

    /**
     * This method is used in settings to simply
     * erase everything from a given database tablename
     */
    public void deleteAllRows() {
        try {
            // build the sql statement
            String sql = "DELETE FROM " + tableName;
            // prepare the statement and execute
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.executeUpdate(); //
            }
        } catch (SQLException e) {
            System.err.println("Error during deleteAllRows " + e.getMessage());
        }
    }

    /**
     * This method finds all the rows from a database table and returns
     * them as a result set, it takes in the following parameters
     *
     * @param condition the condition for the query
     * @param orderByColumn the column to order by,
     * @param orderDirection the direction to order by which is either asc or desc in sql
     * @return a resultSet containing the matching rows
     */
    /**
     * This method finds all the rows from a database table and returns
     * them as a result set, it takes in the following parameters
     *
     * @param condition the condition for the query
     * @param orderByColumn the column to order by,
     * @param orderDirection the direction to order by which is either asc or desc in sql
     * @return a resultSet containing the matching rows
     */
    public ResultSet findAll(String condition, String orderByColumn, String orderDirection) {
        try {
            // build the sql statement
            String sql = "SELECT * FROM " + tableName;

            // check if the conditions are null, this is optional parameter, doesnt always need restriction
            if (condition != null && !condition.isEmpty()) {
                sql += " WHERE " + condition;
            }
            // check if the order  are null, this is optional parameter, doesnt always need restriction
            if (orderByColumn != null && !orderByColumn.isEmpty()) {
                sql += " ORDER BY " + orderByColumn;

                // ensure that if the direction is null, fallback to default DESC
                if (orderDirection == null || orderDirection.isEmpty()) {
                    orderDirection = "DESC";
                    // fallback if the passed parameter is not either ASC or DESC, to default desc
                } else if (!orderDirection.equals("ASC") && !orderDirection.equals("DESC")) {
                    orderDirection = "DESC";
                }

                sql += " " + orderDirection; // concatenate to the sql statement
            }

            // prepare and execute statement
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error during findAll " + e.getMessage());
            return null;
        }
    }

    /**
     * Finds method a single row in the table that matches the specified passed
     * parameter condition
     *
     * @param condition the condition for the query to restrict search by
     * @return a result set with the matching data
     */
    public ResultSet findOne(String condition) {
        try {
            // build the sql statement
            String sql = "SELECT * FROM " + tableName + " WHERE " + condition;
            Statement stmt = connection.createStatement();
            return stmt.executeQuery(sql);
        } catch (SQLException e) {
            System.err.println("Error during findOne " + e.getMessage());
            return null;
        }
    }

    /**
     * Updates a row in the table with with the specified table name
     * field to update and the new value, inserts newValues will have
     * to be casted to string before insert!
     *
     * @param field the field to update
     * @param id primary key for which field to update
     * @param newValue the new value for the field
     */
    public void update(String field, int id, String newValue) {
        try {
            // create the SQL update statement
            String sql = "UPDATE " + tableName + " SET " + field + " = ? WHERE " + primaryKey + " = ?";

            // set the placeholder values with the new values variable, with index of placeholders
            // using setString method
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setString(1, newValue); // set the new value
                stmt.setInt(2, id); // sets the pk value
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Error during update " + e.getMessage());
        }
    }
}

