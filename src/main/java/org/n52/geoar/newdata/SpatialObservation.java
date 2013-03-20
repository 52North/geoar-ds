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

import android.location.Location;

/**
 * 
 * @author Arne de Wall
 *
 */
public class SpatialObservation<E extends Number> extends SpatialEntity {

	/**
	 * Constants
	 */
	private static final long serialVersionUID = 1L;
	
	public final E value;
	
	/*************************************
	 * CONSTRUCTOR
	 ************************************/
	/**
	 * 
	 * @param latitude
	 * @param longitude
	 * @param value
	 */
	public SpatialObservation(final int latitudeE6, final int longitudeE6, final E value) {
		super(latitudeE6, longitudeE6);
		this.value = value;
	}

	public SpatialObservation(final double latitude, final double longitude, final E value) {
		super(latitude, longitude);
		this.value = value;
	}

	public SpatialObservation(final int latitudeE6, final int longitudeE6,
			final int altitude, final E value) {
		super(latitudeE6, longitudeE6, altitude);
		this.value = value;
	}

	public SpatialObservation(final double latitude, final double longitude,
			final double altitude, final E value) {
		super(latitude, longitude, altitude);
		this.value = value;
	}

	@Override
	protected void devicePositionUpdate(Location l) {
		this.distanceToDevice = distanceTo(l.getLatitude(), l.getLongitude());
	}
	
	public E getValue() {
		return value;
	}

}
