/**
 * Simple HTTP handler for testing ChronoTimer
 */
package com.haxorz.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Server {

    // a shared area where we get the POST data and then use it in the other handler
    static String sharedResponse = "";
    static List<AthleteJson> Athletes = new ArrayList<>();
    static HashMap<Integer, String> Competitors = new HashMap<>();

    private static Executor _executor = Executors.newCachedThreadPool();
    private static volatile int _thread_count = 0;
    private static final Object _lock = new Object();
    private static final Semaphore _semaphore = new Semaphore(0);

    private static ServerSocket _socket;

    public static void main(String[] args) throws Exception {
        LoadNames("racers.txt");

        _socket = new ServerSocket(8080);

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

    private static void LoadNames(String fileName){
        String line = null;

        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader =
                    new FileReader(fileName);

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                String[] temp = line.trim().split(",");

                if(temp.length != 2)
                    continue;
                try{
                    int num = Integer.parseInt(temp[0]);
                    if(!Competitors.containsKey(num))
                        Competitors.put(num, temp[1].trim());     }
                catch (NumberFormatException ex){
                    ex.printStackTrace();
                }
            }

            // Always close files.
            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            fileName + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + fileName + "'");
        }
    }

    private static void AwaitConnection(){
        try {
            Socket clientSocket = _socket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);

            synchronized (_lock){
                _thread_count++;
            }

            _semaphore.acquire();

            out.print("refresh");

            clientSocket.close();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    static class HTMLHandler implements HttpHandler{

		@Override
		public void handle(HttpExchange t) throws IOException {
            _executor.execute(Server::AwaitConnection);
			// TODO Auto-generated method stub
			StringBuilder response = new StringBuilder("<!DOCTYPE html>\r\n" +
                    "<html>\r\n" +
                    "<head>\n" +
                    "<script src=\"https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.18.1/moment.min.js\"></script>" +
                    "  <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\">\r\n" +
                    "</head>\r\n" +
                    "<body>\r\n" +
                    "<div id=\"status\" class=\"fail\"></div>\r\n" +
                    "<table id=\"Employees\">\r\n" +
                    "  <tr id=\"Header\">\r\n" +
                    "    <th>Place</th>\r\n" +
                    "    <th>Number</th>\r\n" +
                    "    <th>Name</th>\r\n" +
                    "    <th>Time</th>\r\n" +
                    "  </tr>\r\n");
			

            for(AthleteJson i: Athletes) {

                String name = "";

                if(Competitors.containsKey(i.AthleteNumber))
                    name = Competitors.get(i.AthleteNumber);

				response.append("<tr>\r\n" + "    <td>").append(i.Place).append("</td>\r\n")
                        .append("    <td>").append(i.AthleteNumber).append("</td>\r\n")
                        .append("    <td>").append(name).append("</td>\r\n");

				switch (i.State.toLowerCase()){
                    case "finished":
                        response.append("    <td>").append(i.Time).append("</td>\r\n").append("  </tr>\r\n");
                        break;
                    case "racing":
                        long time;

                        long raceTime = Long.parseLong(i.Time);
                        long timeStamp = Long.parseLong(i.TimeStamp);

                        long cureTime = System.nanoTime();
                        time = raceTime;// + (cureTime - timeStamp);

                        response.append("    <td class=\"timer_div\" data-initial='").append(time).append("'>").append(time).append("</td>\r\n").append("  </tr>\r\n");
                        break;
                    case "waiting":
                        response.append("    <td>").append("Waiting To Race").append("</td>\r\n").append("  </tr>\r\n");
                        break;
                }
			}
			
			response.append("</table>\r\n" + "\r\n" + "</body>\r\n" + "<script>\r\n" + _script + "</script>\r\n" + "</html>");
			
			t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.toString().getBytes());
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

                synchronized (_lock){
                    _semaphore.release(_thread_count);
                    _thread_count = 0;
                }
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

    private static final String _script = "var time_to_add = 0;\n" +
            "var interval = setInterval(function() {\n" +
            "\n" +
            "    var elements = document.getElementsByClassName('timer_div');\n" +
            "        \n" +
            "    time_to_add += 1;\n" +
            "\n" +
            "    for (let index = 0; index < elements.length; index++) {\n" +
            "        const element = elements[index];\n" +
            "        var initial_time = parseInt(element.getAttribute(\"data-initial\"));\n" +
            "                \n" +
            "        element.innerHTML = moment.utc(initial_time + (time_to_add*100)).format('mm:ss:S').toString();           \n" +
            "    }\n" +
            "\n" +
            "}, 100);\n" +
            "\n" +
            "if (window.MozWebSocket) {\n" +
            "    window.WebSocket = window.MozWebSocket;\n" +
            "  }\n" +
            "  \n" +
            "  function openConnection() {\n" +
            "    // uses global 'conn' object\n" +
            "    if (conn.readyState === undefined || conn.readyState > 1) {\n" +
            "      conn = new WebSocket('ws://' + window.location.hostname + ':8080');\n" +
            "      conn.onopen = function () {\n" +
            "        state.className = 'success';\n" +
            "        state.innerHTML = 'Socket open';\n" +
            "      };\n" +
            "  \n" +
            "      conn.onmessage = function (event) {\n" +
            "        location.reload(true);\n" +
            "      };\n" +
            "  \n" +
            "      conn.onclose = function (event) {\n" +
            "        state.className = 'fail';\n" +
            "        state.innerHTML = 'Socket closed';\n" +
            "        location.reload(true);\n" +
            "      };\n" +
            "    }\n" +
            "  }\n" +
            "  \n" +
            "  var conn = {},\n" +
            "      state = document.getElementById('status');\n" +
            "  \n" +
            "  if (window.WebSocket === undefined) {\n" +
            "    state.innerHTML = 'Sockets not supported';\n" +
            "    state.className = 'fail';\n" +
            "  } else {  \n" +
            "    openConnection();\n" +
            "  }";

}