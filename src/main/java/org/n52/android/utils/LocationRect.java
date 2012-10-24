package org.n52.android.utils;

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
public class LocationRect extends RectF implements Serializable {

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
	public LocationRect(float left, float top, float right, float bottom) {
		super(left, top, right, bottom);
		mGeoLocation = new GeoLocation((int) (centerX() * 1E6),
				(int) (centerY() * 1E6));
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
