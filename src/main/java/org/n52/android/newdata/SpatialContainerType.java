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
