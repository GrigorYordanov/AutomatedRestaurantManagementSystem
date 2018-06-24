/**
 * Handles the server's requests. Since the domain and port remain the same to
 * access different server resources the path of the URL must be changed. E.g to
 * perform a SELECT statement then "/database/select" is required. "/database"
 * then lets Jersey (the rest API) know that whatever requests follow will be in
 * the class which has declared @Path("/database") and it will pick according to
 * the HTTP request method.
 * 
 */

package server.httprequest;
