
import com.fasterxml.jackson.databind.*;
import com.sun.net.httpserver.*;
import netscape.javascript.*;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;

public class HttpServerTest {
    public static void main(String[] args) throws IOException {
        //HttpServer server = HttpServer.create(new InetSocketAddress("pub400.com", 8002), 0);
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8003), 0);

        server.createContext("/softlock", new  MyHttpHandler());
        ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        server.setExecutor(threadPoolExecutor);
        server.start();
    }
    private static class MyHttpHandler implements HttpHandler {
        DBConnection dbConnection= new DBConnection();
        @Override
        public void handle(HttpExchange httpExchange) throws IOException {
            String requestParamValue=null;
            List<ChessData> result=null;
            if("GET".equals(httpExchange.getRequestMethod())) {
                requestParamValue = handleGetRequest(httpExchange);
                try {
                     result  =dbConnection.getDataFromDB("SELECT * FROM GAMES400.CHESS where SRCSEQ="+requestParamValue);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else if("UPDATE".equalsIgnoreCase(httpExchange.toString())) {
                requestParamValue = handlePostRequest(httpExchange);
            }
            handleResponse(httpExchange,result);
        }

        private String handlePostRequest(HttpExchange httpExchange) {
            return "PST called";
        }

        private String handleGetRequest(HttpExchange httpExchange) {
            return httpExchange.
            getRequestURI()
                    .toString()
                    .split("\\?")[1]
                    .split("=")[1];
        }
        private void handleResponse(HttpExchange httpExchange, List<ChessData> chessDataList)  throws  IOException {
            OutputStream outputStream = httpExchange.getResponseBody();
            StringBuilder htmlBuilder = new StringBuilder();

//            htmlBuilder.append("<html>").
//            append("<body>").
//            append("<h1>").
//            append("Hello ")
//                    .append(requestParamValue)
//                    .append("</h1>")
//                    .append("</body>")
//                    .append("</html>");
            // encode HTML content
            ObjectMapper mapper = new ObjectMapper();
            String htmlResponse = chessDataList.toString();
            List<String> result = new ArrayList<>();
            for(ChessData c: chessDataList){
                htmlResponse =mapper.writeValueAsString(c);
                result.add(htmlResponse);
            }


            httpExchange.sendResponseHeaders(200, htmlResponse.length());
            outputStream.write(htmlResponse.getBytes());
            outputStream.flush();
            outputStream.close();
        }
    }
}
