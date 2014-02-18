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
package org.n52.geoar.utils;

import java.io.Serializable;

import android.graphics.RectF;

/**
 * LocationRect is derived from {@link RectF} and holds four float coordinates
 * for a rectangle and the center {@link GeoLocation} in microdegrees. The
 * rectangle is represented by the coordinates of its 4 edges (left, top, right
 * bottom)
 * 
 * @author Arne de Wall
 * 
 */
public class GeoLocationRect extends RectF implements Serializable {

	/**************************
	 * Constants
	 **************************/
	private static final long serialVersionUID = 1L;

	/**************************
	 * Variables
	 **************************/
	private GeoLocation mGeoLocation;

	/**************************
	 * Constructor
	 **************************/
	public GeoLocationRect(float left, float top, float right, float bottom) {
		super(left, top, right, bottom);
		mGeoLocation = new GeoLocation((int) (centerY() * 1E6),
				(int) (centerX() * 1E6));
	}

	/**
	 * @return the center Geolocation of the rectangle
	 */
	public GeoLocation getGeoLocation() {
		return mGeoLocation;
	}

	/**
	 * 
	 * @return center latitude in microdegrees.
	 */
	public int getLatitudeE6() {
		return mGeoLocation.mLatitudeE6;
	}

	/**
	 * @return center longitude in microdegrees.
	 */
	public int getLongitudeE6() {
		return mGeoLocation.mLongitudeE6;
	}

	/**
	 * 
	 * @return center latitude.
	 */
	public double getLatitude() {
		return mGeoLocation.mLatitudeE6 / 1E6;
	}

	/**
	 * @return center longitude.
	 */
	public double getLongitude() {
		return mGeoLocation.mLongitudeE6 / 1E6;
	}

}
