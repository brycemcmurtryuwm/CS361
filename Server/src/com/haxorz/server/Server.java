/**
 * Simple HTTP handler for testing ChronoTimer
 */
package com.haxorz.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.haxorz.lab7.DirectoryCmd;
import com.haxorz.lab7.DirectoryEditor;
import com.haxorz.lab7.Employee;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Server {

    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static boolean skipProcessCmd = false;
    static DirectoryEditor directoryEditor;

    public static void main(String[] args) throws Exception {

        directoryEditor = new DirectoryEditor(System.out);

        // set up a simple HTTP server on our local host
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

        // create a context to get the request to display the results
        server.createContext("/displayresults", new DisplayHandler());

        // create a context to get the request for the POST
        server.createContext("/sendresults",new PostHandler());
        server.setExecutor(null); // creates a default executor

        // get it going
        System.out.println("Starting Server...");
        server.start();
    }

    static class DisplayHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {

            String response = "Begin of response\n";
			Gson g = new Gson();
			// set up the header
            System.out.println(response);

			try {
				if (!sharedResponse.isEmpty()) {

                    String[] tmpArr = sharedResponse.trim().split("\\s+");

                    System.out.println(response);
                    response += "Recent Transmission\n";

                    if(tmpArr.length>1){
                        ArrayList<Employee> fromJson = g.fromJson(tmpArr[1],
                                new TypeToken<Collection<Employee>>() {
                                }.getType());

                        Collections.sort(fromJson);
                        for (Employee e : fromJson) {
                            response += e + "\n";
                        }
                    }
                    else {
                        response += sharedResponse + "\n";
                    }


					response += "\nAll Employees\n";
                    List<Employee> employees = directoryEditor.listAllEmployees();
                    Collections.sort(employees);
					for (Employee e : employees) {
						response += e + "\n";
					}
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
			}
            response += "End of response\n";
            System.out.println(response);
            // write out the response
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
            skipProcessCmd = false;

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

            // respond to the POST with ROGER
            String postResponse = "ROGER JSON RECEIVED";

            System.out.println("response: " + sharedResponse);

            //Desktop dt = Desktop.getDesktop();
            //dt.open(new File("raceresults.html"));

            if(sharedResponse.toLowerCase().startsWith("print")){
                skipProcessCmd = true;

                postResponse = "\nAll Employees\n";
                List<Employee> employees = directoryEditor.listAllEmployees();
                Collections.sort(employees);
                for (Employee e : employees) {
                    postResponse += e + "\n";
                }
            }

            // assume that stuff works all the time
            transmission.sendResponseHeaders(300, postResponse.length());

            // write it and return it
            outputStream.write(postResponse.getBytes());

            outputStream.close();

            //-------------------------------

            if(skipProcessCmd)
                return;

            DirectoryCmd cmd = DirectoryCmd.ParseFromString(sharedResponse);

            directoryEditor.executeCmd(cmd);

            //---------------------------------
        }
    }

}