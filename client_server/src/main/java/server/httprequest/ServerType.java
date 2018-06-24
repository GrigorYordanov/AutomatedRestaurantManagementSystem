package server.httprequest;

/**
 * Determines which server to use, localhost or remote.
 * @author Mark Abdel Malak
 *
 */
public enum ServerType {
  REMOTE("http://138.68.172.238:8080/client_server/cafe"), LOCALHOST("http://localhost:8080/client_server/cafe");
  
  private String type;
  private ServerType(String type) {
    this.type = type;
  }
  
  public String toString() {
    return type;
  }
}
