import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    public static void main(String[] args) throws SQLException {
         Properties props;

        try {

            props = new Properties();
            props.load(new FileInputStream("/Users/ayush/study/java_as400/src/main/resources/db.properties"));

            String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
            String URL = "jdbc:as400://" + props.getProperty("local_system").trim() + ";naming=system;errors=full";
            Connection con = null;
            System.out.println("URL " +URL);

            //Connect to iSeries
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, props.getProperty("userId").trim(), props.getProperty("password").trim());
            System.out.println("successfully connected");
            // Commit changes manually
            con.setAutoCommit(false);
            System.out.println("**** Created a JDBC connection to the data source");

            // Create the Statement
            Statement stmt = con.createStatement();
            System.out.println("**** Created JDBC Statement object");

            // Execute a query and generate a ResultSet instance
            ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES400.CHESS");
            System.out.println("**** Created JDBC ResultSet object");
String empNo;
            // Print all of the employee numbers to standard output device
            while (rs.next()) {
                empNo = rs.getString(1);
                System.out.println("Employee number = " + empNo);
            }
            System.out.println("**** Fetched all rows from JDBC ResultSet");
            // Close the ResultSet
            rs.close();
            System.out.println("**** Closed JDBC ResultSet");

            // Close the Statement
            stmt.close();
            System.out.println("**** Closed JDBC Statement");

            // Connection must be on a unit-of-work boundary to allow close
            con.commit();
            System.out.println ( "**** Transaction committed" );

            // Close the connection
            con.close();
            System.out.println("**** Disconnected from data source");

            System.out.println("**** JDBC Exit from class EzJava - no errors");

        }

        catch (ClassNotFoundException | IOException e)
        {
            System.err.println("Could not load JDBC driver");
            System.out.println("Exception: " + e);
            e.printStackTrace();
        }


    }
}
