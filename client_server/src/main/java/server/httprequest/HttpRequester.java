package server.httprequest;
//Code altered HTTP CLient from https://www.mkyong.com/java/how-to-send-http-request-getpost-in-java/

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Used to make HTTP requests to the server.
 * @author Mkyong (Original author)
 * @author Mark Abdel Malak
 * 
 */
public class HttpRequester {

  private final String userAgent = "Mozilla/5.0";

  /**
   * Send a GET request to the server. The beginning of the URL string is the server 
   * {@link ServerType} 
   * and the URL argument is the specific server resource which is required.  
   * @param url which will receive the GET request.
   * @throws MalformedURLException is thrown if there is an issue with the URL.
   * @throws IOException is thrown if there is an issue with the connection.
   */
  public String sendGet(final String url) throws MalformedURLException, IOException {

    URL obj = new URL(ServerType.REMOTE + url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    // optional default is GET
    con.setRequestMethod("GET");

    //add request header
    con.setRequestProperty("User-Agent", userAgent);
    
    //Debugging
    //getHttpResponse(con, url, "");

    BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();
    System.out.println(response.toString());
    
    return response.toString();
  }
  
  /**
   * Send a POST request to the server. The beginning of the URL string is the server 
   * {@link ServerType} 
   * and the URL argument is the specific server resource which is required.  
   * @param url which will receive the POST request.
   * @throws MalformedURLException is thrown if there is an issue with the URL.
   * @throws IOException is thrown if there is an issue with the connection.
   */
  public String sendPost(final String url, final String urlParameters) throws MalformedURLException,
      IOException {
    URL obj = new URL(ServerType.REMOTE + url);
    HttpURLConnection con = (HttpURLConnection) obj.openConnection();

    //HTTP method request type.
    con.setRequestMethod("POST");
    //The type that is being received.
    con.setRequestProperty("Accept", "application/json");
    //The type that is being sent in the POST request.
    con.setRequestProperty("Content-Type", "text/plain");

    // Send post request
    con.setDoOutput(true);
    DataOutputStream wr = new DataOutputStream(con.getOutputStream());
    wr.writeBytes(urlParameters);
    wr.flush();
    wr.close();

    //Debugging code.
    //getHttpResponse(con, url, urlParameters);
    
    BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
    String inputLine;
    StringBuffer response = new StringBuffer();

    while ((inputLine = in.readLine()) != null) {
      //added new line.
      response.append(inputLine + "\n");
    }
    in.close();

    System.out.println(response.toString());
    return response.toString();
  }
  
  /**
   * Finds the HTTP status code of a HTTP request. This is used for debugging.
   * @param con the connection that will hold the URL and perform the HTTP method request.
   * @param url is which URL will receive the HTTP method request.
   * @param urlParameters the is the data thats being sent.
   * @throws IOException if there is an issue with the connection.
   */
  
  private void getHttpResponse(HttpURLConnection con, String url, String urlParameters) throws 
      IOException {
    int responseCode = con.getResponseCode();
    System.out.println("\nSending 'POST' request to URL : " + url);
    if (!urlParameters.equals("")) {
      System.out.println("Post parameters : " + urlParameters);      
    }
    System.out.println("Response Code : " + responseCode);
  }
}