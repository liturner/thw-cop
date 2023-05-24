package de.turnertech.thw.cop.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class PolygonDecoder implements GmlDecoder<Polygon> {

    @Override
    public Polygon decode(Node root) {
        Polygon returnPolygon = new Polygon();
        
        Node srsNode = root.getAttributes().getNamedItem("srsName");
        if(srsNode != null) {
            // TODO: Actually Decode this and set it
        }

        NodeList children = root.getChildNodes();
        for(int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if(child.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            if("exterior".equals(child.getNodeName())) {
                // TODO: Defer to a "LinearRingDecoder"
            }
        }


        return returnPolygon;
    }
    
}
