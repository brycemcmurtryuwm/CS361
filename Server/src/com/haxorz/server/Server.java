/**
 * Simple HTTP handler for testing ChronoTimer
 */
package com.haxorz.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Server {

    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static List<AthleteJson> Athletes = new ArrayList<>();
    static HashMap<Integer, String> Competitors = new HashMap<>();

    public static void main(String[] args) throws Exception {

        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // create a context to get the request to display the results
        server.createContext("/displayresults/directory", new HTMLHandler());

        server.createContext("/displayresults/style.css", new CSSHandler());

        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
        server.setExecutor(null); // creates a default executor

        // get it going
        System.out.println("Starting Server...");
        server.start();
    }
    
    static class HTMLHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange t) throws IOException {
			// TODO Auto-generated method stub
			String response = "<!DOCTYPE html>\r\n" + 
					"<html>\r\n" +
                    "<head>\n" +
                    "  <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\r\n" +
                    "</head>\r\n" +
					"<body>\r\n" + 
					"<table id=\"Employees\">\r\n" + 
					"  <tr id=\"Header\">\r\n" + 
					"    <th>Place</th>\r\n" +
					"    <th>Number</th>\r\n" +
					"    <th>Name</th>\r\n" +
					"    <th>Time</th>\r\n" +
					"  </tr>\r\n";
			

            for(AthleteJson i: Athletes) {

                String name = "";

                if(Competitors.containsKey(i.AthleteNumber))
                    name = Competitors.get(i.AthleteNumber);

				response += "<tr>\r\n" + 
						"    <td>"+ i.Place +"</td>\r\n" +
						"    <td>"+ i.AthleteNumber +"</td>\r\n" +
						"    <td>"+ name +"</td>\r\n" +
						"    <td>"+ i.Time +"</td>\r\n" +
						"  </tr>\r\n";
			}
			
			response += "</table>\r\n" + 
					"\r\n" + 
					"</body>\r\n" + 
					"</html>";
			
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
		}
    	
    }

    static class CSSHandler implements HttpHandler{

        @Override
        public void handle(HttpExchange t) throws IOException {
            // TODO Auto-generated method stub
            String response = "#Employees {\n" +
                    "    font-family: \"Trebuchet MS\", Arial, Helvetica, sans-serif;\n" +
                    "    border-collapse: collapse;\n" +
                    "    width: 100%;\n" +
                    "}\n" +
                    "\n" +
                    "#Employees td, #Header th {\n" +
                    "    border: 1px solid #ddd;\n" +
                    "    padding: 8px;\n" +
                    "}\n" +
                    "\n" +
                    "#Employees tr:nth-child(even){background-color: #bfbfbf;}\n" +
                    "\n" +
                    "#Employees tr:hover {background-color: #ddd;}\n" +
                    "\n" +
                    "#Header th {\n" +
                    "    padding-top: 12px;\n" +
                    "    padding-bottom: 12px;\n" +
                    "    text-align: left;\n" +
                    "    background-color: #009933;\n" +
                    "    color: white;\n" +
                    "}";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }

    static class PostHandler implements HttpHandler {
        public void handle(HttpExchange transmission) throws IOException {

            //  shared data that is used with other handlers
            sharedResponse = "";

            // set up a stream to read the body of the request
            InputStream inputStr = transmission.getRequestBody();

            // set up a stream to write out the body of the response
            OutputStream outputStream = transmission.getResponseBody();

            // string to hold the result of reading in the request
            StringBuilder sb = new StringBuilder();

            // read the characters from the request byte by byte and build up the sharedResponse
            int nextChar = inputStr.read();
            while (nextChar > -1) {
                sb=sb.append((char)nextChar);
                nextChar=inputStr.read();
            }

            // create our response String to use in other handler
            sharedResponse = sharedResponse+sb.toString();

            try {

                Gson g =  new Gson();
                Athletes = g.fromJson(sharedResponse,
                        new TypeToken<Collection<AthleteJson>>() {
                        }.getType());

            }
            catch (Exception e){System.out.println(e.getMessage());}
            // respond to the POST with ROGER
            String postResponse = "ROGER JSON RECEIVED";

            System.out.println("response: " + sharedResponse);

            //Desktop dt = Desktop.getDesktop();
            //dt.open(new File("raceresults.html"));

            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();

            //---------------------------------
        }
    }

}