package de.turnertech.thw.cop.gml;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import javax.xml.stream.XMLStreamWriter;

import de.turnertech.thw.cop.Logging;

/**
 * <p>gml:LinearRing</p>
 */
public class LinearRing implements GmlElement, Iterable<DirectPosition> {
    
    public static final String GML_NAME = "LinearRing";

    private DirectPositionList posList;

    public LinearRing() {
        this(new DirectPositionList());
    }

    public LinearRing(DirectPositionList posList) {
        this.posList = posList;
    }

    public DirectPositionList getPosList() {
        return posList;
    }

    public void setPosList(DirectPositionList posList) {
        this.posList = posList;
    }

    public boolean add(DirectPosition e) {
        return posList.add(e);
    }

    public boolean remove(DirectPosition o) {
        return posList.remove(o);
    }

    public int size() {
        return posList.size();
    }

    @Override
    public void writeGml(XMLStreamWriter out, String prefix, String localName, String namespaceURI) {
        try {
            writeGmlStartElement(out, prefix, localName, namespaceURI);
            
            posList.writeGml(out);

            out.writeEndElement();
        } catch (Exception e) {
            Logging.LOG.severe("Could not get GML for LinearRing");
        }        
    }

    @Override
    public String getGmlName() {
        return GML_NAME;
    }

    @Override
    public Iterator<DirectPosition> iterator() {
        return posList.iterator();
    }

    @Override
    public void forEach(Consumer<? super DirectPosition> action) {
        posList.forEach(action);
    }

    @Override
    public Spliterator<DirectPosition> spliterator() {
        return posList.spliterator();
    }

}
