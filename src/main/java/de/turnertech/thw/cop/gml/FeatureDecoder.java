package de.turnertech.thw.cop.gml;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.turnertech.thw.cop.ows.api.OwsContext;

public class FeatureDecoder {

    public static IFeature decode(Node xmlRootNode, OwsContext owsContext, FeatureType featureType) {
        Feature returnFeature = featureType.createInstance();

        NodeList propertyNodes = xmlRootNode.getChildNodes();
        for(int i = 0; i < propertyNodes.getLength(); ++i) {
            Node propertyNode = propertyNodes.item(i);
            if(propertyNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }

            String propertyName = propertyNode.getNodeName();
            String propertyNamespace = propertyNode.getNamespaceURI();

            //TODO: Here we need to rely on the FeatureType, and its map of FeatureProperty instances, which will
            //contain type information needed for handling casting to the correct data type.
            if(!featureType.hasProperty(propertyName)) {
                // TODO: Throw Exception
                return null;
            }
            
            

        }
        return returnFeature;
    }
    
}
