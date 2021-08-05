import java.io.*;
import java.sql.*;
import java.util.*;

public class DBConnection {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        String application_properties = File.separator + "config" + File.separator + "db.properties";
        String application_properties1 =  "."+File.separator + "db.properties";
        String application_properties2 = ".\\db.properties";
        String application_properties3= "C:\\Users\\ayush\\project\\As400\\config\\db.properties";
        String application_properties4= "\\classes\\main\\resources\\db.properties";
        System.out.println("application path "+application_properties);
        System.out.println("application path "+application_properties1);
        System.out.println("application path "+application_properties2);

        Properties prop = getProperty(application_properties4);
        System.out.println("Starting connection to AS400");
        System.out.println(prop.toString());
        String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
        String URL = "jdbc:as400://" + prop.getProperty("local_system").trim() + ";naming=system;errors=full";
        Connection con = null;
        System.out.println("URL " + URL);

        //Connect to iSeries
        Class.forName(DRIVER);
        con = DriverManager.getConnection(URL, prop.getProperty("userId").trim(), prop.getProperty("password").trim());
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
        System.out.println("**** Transaction committed");

        // Close the connection
        con.close();
        System.out.println("**** Disconnected from data source");

        System.out.println("**** JDBC Exit from class EzJava - no errors");

    }




    public static Properties getProperty(String propFile) {
        Properties properties = new Properties();
        InputStream iStream = null;
        String path = System.getProperty("user.dir");
        System.out.println("USER dir "+path);

        // String propFile = "/application.properties";
        try {

            System.out.println("File path "+path + propFile);
            InputStream inputStream =
                    new FileInputStream(new File( path+propFile));
            properties.load(inputStream);
        } catch (IOException e) {

        } finally {
            try {
                if (iStream != null) {
                    iStream.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
            }
        }
        return properties;
    }

}
