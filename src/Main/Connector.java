package Main;

import java.sql.*;

abstract class Connector {

    private static Connection theconnection = null;
    private final static String URL = "jdbc:mysql://localhost/doubledouble";
    private final static String USER = "root";
    private final static String PASSWORD = "root";
    private static String message;
    private final static String insertBlurb = "INSERT INTO AppNewIn2 (firstname,lastname,age,compnany,occupation,birthday,address,city,postalcode,industry) VALUES (";
    private static StringBuilder insertQuery = new StringBuilder(insertBlurb);

    private Connector() throws SQLException {
    }

    private static void setMessage(String message) {
        Connector.message = message;
    }

    public static String getMessage() {
        return message;
    }

    private static String connectionResponse(Connection connection) {
        System.out.println(connection);

        if (connection == null) {
            setMessage("No connection.");
        } else {
            setMessage("You are connected.");
        }
        return message;
    }


    public static void getTheconnection() throws SQLException {

        if (theconnection == null) {

            try {
                theconnection = DriverManager.getConnection(URL,USER,PASSWORD);

            } catch (SQLException e) {
                System.out.println("Unable to connect. Error: " + e.getMessage());
            }
        }

        connectionResponse(theconnection);
    }

    public static boolean createStmt(String... query) throws SQLException {

        int counter = 0;

        for (String s: query) {
            counter++;

            insertQuery.append("'" + s +"'");

            if (counter != query.length) {
                insertQuery.append(",");
            }
        }
        insertQuery.append(");");

        System.out.println(insertQuery.toString());

        Statement stmt = null;
        stmt = theconnection.createStatement();
        int result = stmt.executeUpdate(insertQuery.toString());

        if (result > 0) {
            insertQuery.setLength(0);           // Clear stringbuilder
            insertQuery.append(insertBlurb);    // insert constant content into stringbuilder
            return true;
        }

        return false;
    }

}
