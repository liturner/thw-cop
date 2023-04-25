package de.turnertech.thw.cop;

import java.io.IOException;
import java.io.PrintWriter;

import de.turnertech.thw.cop.wfs.ExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * We use OWS for everything, because "why not?"
 */
public class ErrorServlet extends HttpServlet {
 
    // Method to handle GET method request.
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   
        // Analyze the servlet exception
        // Throwable throwable = (Throwable) request.getAttribute("jakarta.servlet.error.exception");
        // Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");
        // String requestUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
        String message = (String) request.getAttribute("jakarta.servlet.error.message");
        ExceptionCode owsExceptionCode = ExceptionCode.valueOfIgnoreCase(message);

        // Set response content type
        response.setContentType(Constants.ContentTypes.XML);
 
        PrintWriter out = response.getWriter();
        out.print("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.print("<ows:ExceptionReport version=\"2.0.2\" lang=\"en-GB\" xmlns:ows=\"http://www.opengis.net/ows\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.opengis.net/ows http://schemas.opengis.net/ows/1.0.0/owsExceptionReport.xsd\">");
        out.print("<ows:Exception exceptionCode=\"");
        out.print(owsExceptionCode.toString());
        out.print("\"");
        if(!(owsExceptionCode == ExceptionCode.NO_APPLICABLE_CODE || owsExceptionCode == ExceptionCode.NONE)) {
            out.print(" locator=\"Not Yet Implemented\"");
        }
        out.print("/></ows:ExceptionReport>");
    }
    
    // Method to handle POST method request.
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
       
        doGet(request, response);
    }
}