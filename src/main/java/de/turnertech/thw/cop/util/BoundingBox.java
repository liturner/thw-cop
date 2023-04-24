package de.turnertech.thw.cop.util;

import java.io.StringWriter;
import java.util.List;
import java.util.Optional;

public class BoundingBox {

    protected double south;
    
    protected double west;
    
    protected double north;
    
    protected double east; 

    public BoundingBox(double south, double west, double north, double east) {
        this.south = south;
        this.west = west;
        this.north = north;
        this.east = east;
    }

    public boolean contains(double latitude, double longitute) {
        return !(latitude > north || latitude < south || longitute > east || longitute < east);
    }

    public String toGmlString() {
        StringWriter stringWriter = new StringWriter();
        stringWriter.write("<gml:boundedBy><gml:Envelope srsName=\"urn:ogc:def:crs:EPSG::4326\"><gml:lowerCorner>");
        stringWriter.write(String.valueOf(south));
        stringWriter.write(" ");
        stringWriter.write(String.valueOf(west));
        stringWriter.write("</gml:lowerCorner><gml:upperCorner>");
        stringWriter.write(String.valueOf(north));
        stringWriter.write(" ");
        stringWriter.write(String.valueOf(east));
        stringWriter.write("</gml:upperCorner></gml:Envelope></gml:boundedBy>");
        return stringWriter.toString();
    }

    public static Optional<BoundingBox> from(List<? extends PositionProvider> positions) {
        if(positions.isEmpty()) {
            return Optional.empty();
        }

        double maxSouth = Double.MAX_VALUE;
        double maxWest = Double.MAX_VALUE;
        double maxNorth = Double.MIN_VALUE;
        double maxEast = Double.MIN_VALUE;

        for(PositionProvider position : positions) {
            if(position.getLatitude() > maxNorth) maxNorth = position.getLatitude();
            if(position.getLatitude() < maxSouth) maxSouth = position.getLatitude();
            if(position.getLongitude() > maxEast) maxEast = position.getLongitude();
            if(position.getLongitude() < maxWest) maxWest = position.getLongitude();
        }

        // Catch BBOX with a size of 0 (causes errors in many clients, happens with only 1 point)
        if(maxSouth == maxNorth) {
            maxNorth += 0.0001;
            maxSouth -= 0.0001;
        }
        if(maxEast == maxWest) {
            maxEast += 0.0001;
            maxWest -= 0.0001;
        }

        return Optional.of(new BoundingBox(maxSouth, maxWest, maxNorth, maxEast));
    }

}
