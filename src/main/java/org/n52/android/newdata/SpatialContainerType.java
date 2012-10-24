package org.n52.android.newdata;

import java.util.List;

public abstract class SpatialContainerType extends SpatialEntity {

    /**
     * Constatants
     */
    private static final long serialVersionUID = 1L;

    public SpatialContainerType(double latitude, double longitude) {
	super(latitude, longitude);
	// TODO Auto-generated constructor stub
    }

    class SpatialObservation<E extends Number> {
	double longitude;
	double latitude;
	double altitude;
	
	float accuracy;
	
	E value;
	
	
    }
    
    private List<SpatialObservation> observations;

    public void addObservation(double latitude, double longitude, double altitude) {
	if(observations != null ){
	    SpatialObservation so = new SpatialObservation();
	    so.longitude = longitude;
	    so.latitude = latitude;
	    so.altitude = altitude;
	    observations.add(so);
	}

    }

}
