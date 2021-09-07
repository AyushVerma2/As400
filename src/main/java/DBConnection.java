import netscape.javascript.*;

import java.io.*;
import java.sql.*;
import java.util.*;

public class DBConnection {
    public List<ChessData> getDataFromDB(String data) throws SQLException, ClassNotFoundException {
          Connection con = getConnection();
         Statement stmt = con.createStatement();
         return getResult(data,stmt);
    }

    private List<ChessData> getResult(String data, Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery(data);
        String srcseq1=null;
        String srcseq2=null;
        String srcseq3=null;
        // Print all of the employee numbers to standard output device
        List<ChessData> chessDataList = new ArrayList<>();
        while (rs.next()) {
            ChessData chessData = new ChessData();
             chessData.setSRCSEQ(rs.getDouble(1));
             chessData.setSRCDAT(rs.getDouble(2));
             chessData.setSRCDTA(rs.getString(3));
            chessDataList.add(chessData);
        }
        // Close the ResultSet
        rs.close();
        return chessDataList;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection con = getConnection();
        // Create the Statement
        Statement stmt = con.createStatement();
        // Execute a query and generate a ResultSet instance
        ResultSet rs = stmt.executeQuery("SELECT * FROM GAMES400.CHESS where SRCSEQ=340.00 ");
        String srcseq1;
        String srcseq2;
        String srcseq3;
        // Print all of the employee numbers to standard output device
        while (rs.next()) {
            srcseq1 = rs.getString(1);
            srcseq2 = rs.getString(2);
            srcseq3 = rs.getString(3);
            System.out.println("srcseq number 1 = " + srcseq1);
            System.out.println("srcseq number 2 = " + srcseq2);
            System.out.println("srcseq number 3 = " + srcseq3);
        }
        // Close the ResultSet
        rs.close();
//        System.out.println("**** Closed JDBC ResultSet");

        // update
        //rs = stmt.executeQuery("update GAMES400.CHESS set SRCDTA='testing update' where SRCSEQ=340.00 ");
        // Close the Statement
        stmt.close();

        // Connection must be on a unit-of-work boundary to allow close
        con.commit();

        // Close the connection
        con.close();

        System.out.println("**** JDBC Exit from class EzJava - no errors");

    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        //String application_properties3 = "C:\\Users\\ayush\\project\\As400\\config\\db.properties";
       // Properties prop = getProperty(application_properties3);
        System.out.println("Starting connection to PUB400 AS400");
        String DRIVER = "com.ibm.as400.access.AS400JDBCDriver";
        System.out.println("DRIVER   " + DRIVER);
        String URL = "jdbc:as400://PUB400.com" +";naming=system;errors=full";
        System.out.println("URL   " + URL);
        Connection con = null;
        //Connect to iSeries
        Class.forName(DRIVER);
        System.out.println("DRIVER loaded   ");
        con = DriverManager.getConnection(URL, "AYU2211", "Hope07!!");
        System.out.println("successfully connected");
        // Commit changes manually
        con.setAutoCommit(false);
        return con;
    }


    public static Properties getProperty(String propFile) {
        Properties properties = new Properties();
        InputStream iStream = null;
        String path = System.getProperty("user.dir");
        System.out.println("USER dir " + path);

        // String propFile = "/application.properties";
        try {

            System.out.println("File path " + path + propFile);
            InputStream inputStream =
                    new FileInputStream(new File(propFile));
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
