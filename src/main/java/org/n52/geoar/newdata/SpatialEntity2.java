/**
 * Copyright 2012 52°North Initiative for Geospatial Open Source Software GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.n52.geoar.newdata;

import java.io.Serializable;

import android.location.Location;

import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Point;

/**
 * 
 * @author Arne de Wall <a.dewall@52North.org>
 *
 */
public class SpatialEntity2<G extends Geometry> implements LocationUpdateListener, Serializable,
        Cloneable {
    private static final long serialVersionUID = 1L;
    
    protected G geometry;
    protected float[] relPositionVec;
    protected boolean isVisible;

    public SpatialEntity2(G geometry){
        this.geometry = geometry;
    }
    
    @Override
    public void devicePositionUpdate(Location l, boolean changeViewToAR) {
        if (isVisible || changeViewToAR) {
            calculateRelativePositionVector(l.getLatitude(), l.getLongitude(),
                    l.getAltitude());
            isVisible = true;
        }
    }
    
    public int distanceTo(final double otherLat, final double otherLon){
        Point p = geometry.getCentroid();
        float[] res = new float[1];
        Location.distanceBetween(p.getY(), p.getX(), otherLat, otherLon, res);
        return (int) res[0];
    }
    
    private void calculateRelativePositionVector(final int deviceLatitudeE6,
            final int deviceLongitudeE6, final int altitude) {
        calculateRelativePositionVector(deviceLatitudeE6 / 1E6,
                deviceLongitudeE6 / 1E6, altitude);
    }
    
    /**
     * Calculates the relative virtual position vector from device position to
     * the position of the {@link SpatialEntity}.
     * 
     * @param otherLatitude
     *            end latitude of the spatial object
     * @param otherLongitude
     *            end longitude of the spatial object
     * @param otherAltitude
     *            altitude of the spatial object
     */
    private void calculateRelativePositionVector(final double otherLatitude,
            final double otherLongitude, final double otherAltitude) {
        final Point p = geometry.getCentroid();
        
        final double thisLatitude = p.getY();
        final double thisLongitude = p.getX();
        final double thisAltitude = 0; 

        float[] x = new float[1]; // x - direction of vector
        Location.distanceBetween(otherLatitude, otherLongitude, otherLatitude,
                thisLongitude, x);

        float[] z = new float[1]; // y - direction of vector
        Location.distanceBetween(otherLatitude, otherLongitude, thisLatitude,
                otherLongitude, z);

        // correct the direction according to the poi location, because we just
        // get the distance in x and z direction
        if (otherLongitude < thisLongitude)
            x[0] *= -1;
        if (otherLatitude < thisLatitude)
            z[0] *= -1;

        relPositionVec = new float[] {
                x[0],
                (float) (thisAltitude == 0 ? otherAltitude : thisAltitude
                        - otherAltitude), z[0] };
    }

    public G getGeometry() {
        return geometry;
    }

    public void setGeometry(G geometry) {
        this.geometry = geometry;
    }

    public float[] getRelPositionVec() {
        return relPositionVec;
    }

    public void setRelPositionVec(float[] relPositionVec) {
        this.relPositionVec = relPositionVec;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }
    
    public double getLongitude(){
        return this.geometry.getCentroid().getCoordinate().x;
    }
    
    public double getLatitude(){
        return this.geometry.getCentroid().getCoordinate().y;
    }
    
    public double getAltitude(){
        return this.geometry.getCentroid().getCoordinate().z;
    }
    
}
