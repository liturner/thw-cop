package de.turnertech.thw.cop.ows;

import java.io.IOException;

import de.turnertech.thw.cop.Logging;
import de.turnertech.thw.cop.ows.parameter.WfsRequestParameter;
import de.turnertech.thw.cop.ows.parameter.WfsRequestValue;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class WfsFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final HttpServletRequest httpRequest = (HttpServletRequest) request;
        final HttpServletResponse httpResponse = (HttpServletResponse) response;

        final String contentType = httpRequest.getContentType();
        final String wfsRequestString = WfsRequestParameter.findValue(httpRequest, WfsRequestParameter.REQUEST).orElse(WfsRequestParameter.NONE.toString());
        WfsRequestValue wfsRequestValue = WfsRequestValue.valueOfIgnoreCase(wfsRequestString);

        /*if(wfsRequestValue == WfsRequestValue.NONE) {
            //httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, ExceptionCode.OPERATION_NOT_SUPPORTED.toString());
        } else */if(wfsRequestValue == WfsRequestValue.GET_FEATURE) {
            doGetFeatureFilter(httpRequest, httpResponse, chain);
            return;
        } else if(wfsRequestValue == WfsRequestValue.TRANSACTION) {
            doTransactionFilter(httpRequest, httpResponse, chain);
            return;
        }
        chain.doFilter(request, response);
    }

    /**
     * See OGC 09-025r2 Table 8.
     * 
     * TYPENAMES is mandatory, all else is optional.
     * 
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
    */
    private void doGetFeatureFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    
        chain.doFilter(request, response);
    }

    private void doTransactionFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        
        Logging.LOG.info("request=Transaction");

        chain.doFilter(request, response);
    }
}