package de.turnertech.ows.gml;

import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.ows.Logging;

/**
 * gml:posList
 */
public class DirectPositionList extends ArrayList<DirectPosition> implements GmlElement, BoundingBoxProvider {
    
    private SpatialReferenceSystem srs;

    public DirectPositionList() {
        this(10);
    }

    public DirectPositionList(int initialCapacity) {
        super(initialCapacity);
        this.srs = SpatialReferenceSystem.EPSG4326;
    }

    public DirectPositionList(DirectPosition... positions) {
        super(Arrays.asList(positions));
        this.srs = SpatialReferenceSystem.EPSG4326;
    }

    public SpatialReferenceSystem getSrs() {
        return srs;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void writeGml(XMLStreamWriter out, String localName, String namespaceURI, SpatialReferenceSystemRepresentation srs) {
        try {
            writeGmlStartElement(out, localName, namespaceURI);
            out.writeAttribute("srsDimension", "2");
            for(int i = 0; i < this.size(); ++i) {
                out.writeCharacters(Double.toString(this.get(i).getY()) + " " + Double.toString(this.get(i).getX()));
                if(i != this.size() - 1) {
                    out.writeCharacters(" ");
                }
            }
            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for DirectPositionList");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getGmlName() {
        return "posList";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((srs == null) ? 0 : srs.hashCode());
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        DirectPositionList other = (DirectPositionList) obj;
        return srs == other.srs;
    }

    @Override
    public BoundingBox getBoundingBox() {
        return BoundingBox.from(this);
    }

}
