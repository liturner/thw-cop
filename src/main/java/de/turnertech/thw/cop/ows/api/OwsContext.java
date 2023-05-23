package de.turnertech.thw.cop.ows.api;

public interface OwsContext {

    public static final String OWS_URI = "http://www.opengis.net/ows/1.1";
    public static final String WFS_URI = "http://www.opengis.net/wfs/2.0";
    public static final String XSI_URI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSD_URI = "http://www.w3.org/2001/XMLSchema";
    public static final String GML_URI = "http://www.opengis.net/gml/3.2";
    public static final String FES_URI = "http://www.opengis.net/fes/2.0";

    public ModelProvider getModelProvider();

    public WfsCapabilities getWfsCapabilities();

    public String getXmlNamespacePrefix(String namespace);

    public String getXmlNamespaceSchema(String namespace);

}