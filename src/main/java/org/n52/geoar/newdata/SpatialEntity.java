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
///**
// * Copyright 2012 52°North Initiative for Geospatial Open Source Software GmbH
// *
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// *
// *    http://www.apache.org/licenses/LICENSE-2.0
// *
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package org.n52.geoar.newdata;
//
//import java.io.Serializable;
//
//import android.location.Location;
//
//import com.vividsolutions.jts.geom.Geometry;
//
///**
// * Holds the pose information and a geometrical representation to the super
// * class.
// * 
// * @author Arne de Wall
// * 
// */
//public abstract class SpatialEntity implements LocationUpdateListener,
//		Serializable, Cloneable {
//
//	private static final long serialVersionUID = 1L;
//
//	// ================================
//	// Fields
//	// ================================
//	protected boolean isVisible;
//	
//	protected Geometry geometry;
//
//	protected int mLatitudeE6; // latitude, measured in microdegrees (degrees *
//								// 1E6).
//	protected int mLongitudeE6; // longitude, measured in microdegrees (degrees
//								// * 1E6).
//	protected int mAltitude;
//
//	protected float locationAccuracy;
//	protected float distanceToDevice;
//
//	protected float[] relPositionVec;
//
//	// ================================
//	// Constructors
//	// =================================
//
//	/**
//	 * 
//	 * @param latitudeE6
//	 * @param longitudeE6
//	 */
//	public SpatialEntity(final int latitudeE6, final int longitudeE6) {
//		this.mLatitudeE6 = latitudeE6;
//		this.mLongitudeE6 = longitudeE6;
//	}
//
//	public SpatialEntity(final double latitude, final double longitude) {
//		this.mLatitudeE6 = (int) (latitude * 1E6);
//		this.mLongitudeE6 = (int) (longitude * 1E6);
//	}
//
//	public SpatialEntity(final int latitudeE6, final int longitudeE6,
//			final int altitude) {
//		this(latitudeE6, longitudeE6);
//		this.mAltitude = altitude;
//	}
//
//	public SpatialEntity(final double latitude, final double longitude,
//			final double altitude) {
//		this(latitude, longitude);
//		this.mAltitude = (int) altitude;
//	}
//
//	// ================================
//	// Methods
//	// ================================
//
//	/**
//	 * 
//	 * @param otherLatitudeE6
//	 *            Latitude value, measured in microdegrees (degrees * 1E6).
//	 * @param otherLongitudeE6
//	 *            Longitude, measured in microdegrees (degrees * 1E6).
//	 * @return the distance in Meters
//	 */
//	public int distanceTo(final int otherLatitudeE6, final int otherLongitudeE6) {
//		return distanceTo(otherLatitudeE6 / 1E6, otherLongitudeE6 / 1E6);
//	}
//
//	/**
//	 * 
//	 * @param otherLatitude
//	 * @param otherLongitude
//	 * @return
//	 */
//	public int distanceTo(final double otherLatitude,
//			final double otherLongitude) {
//		float[] res = new float[1];
//		Location.distanceBetween(mLatitudeE6 / 1E6, mLongitudeE6 / 1E6,
//				otherLatitude, otherLongitude, res);
//		return (int) res[0];
//	}
//
//	private void calculateRelativePositionVector(final int deviceLatitudeE6,
//			final int deviceLongitudeE6, final int altitude) {
//		calculateRelativePositionVector(deviceLatitudeE6 / 1E6,
//				deviceLongitudeE6 / 1E6, altitude);
//	}
//
//	/**
//	 * Calculates the relative virtual position vector from device position to
//	 * the position of the {@link SpatialEntity}.
//	 * 
//	 * @param otherLatitude
//	 *            end latitude of the spatial object
//	 * @param otherLongitude
//	 *            end longitude of the spatial object
//	 * @param otherAltitude
//	 *            altitude of the spatial object
//	 */
//	private void calculateRelativePositionVector(final double otherLatitude,
//			final double otherLongitude, final double otherAltitude) {
//		final double thisLatitude = mLatitudeE6 / 1E6;
//		final double thisLongitude = mLongitudeE6 / 1E6;
//		final double thisAltitude = mAltitude;
//
//		float[] x = new float[1]; // x - direction of vector
//		Location.distanceBetween(otherLatitude, otherLongitude, otherLatitude,
//				thisLongitude, x);
//
//		float[] z = new float[1]; // y - direction of vector
//		Location.distanceBetween(otherLatitude, otherLongitude, thisLatitude,
//				otherLongitude, z);
//
//		// correct the direction according to the poi location, because we just
//		// get the distance in x and z direction
//		if (otherLongitude < thisLongitude)
//			x[0] *= -1;
//		if (otherLatitude < thisLatitude)
//			z[0] *= -1;
//
//		relPositionVec = new float[] {
//				x[0],
//				(float) (thisAltitude == 0 ? otherAltitude : thisAltitude
//						- otherAltitude), z[0] };
//	}
//
//	@Override
//	public void devicePositionUpdate(Location l, boolean changeViewToAR) {
//		if (isVisible || changeViewToAR) {
//			calculateRelativePositionVector(l.getLatitude(), l.getLongitude(),
//					l.getAltitude());
//			isVisible = true;
//		}
//	}
//
//	protected void devicePositionUpdate(Location l) {
//		this.distanceToDevice = distanceTo(l.getLatitude(), l.getLongitude());
//	}
//
//	// ================================
//	// Getter / Setter
//	// ================================
//
//	/**
//	 * @return the mLatitudeE6
//	 */
//	public int getLatitudeE6() {
//		return mLatitudeE6;
//	}
//
//	/**
//	 * @return Latitude not in microdegrees
//	 */
//	public float getLatitude() {
//		return mLatitudeE6 / 1E6f;
//	}
//
//	/**
//	 * @param mLatitudeE6
//	 *            the mLatitudeE6 to set
//	 */
//	public void setLatitudeE6(int mLatitudeE6) {
//		this.mLatitudeE6 = mLatitudeE6;
//	}
//
//	/**
//	 * @return the mLongitudeE6
//	 */
//	public int getLongitudeE6() {
//		return mLongitudeE6;
//	}
//
//	/**
//	 * @return Longitude not in microdegrees
//	 */
//	public float getLongitude() {
//		return mLongitudeE6 / 1E6f;
//	}
//
//	/**
//	 * @param mLongitudeE6
//	 *            the mLongitudeE6 to set
//	 */
//	public void setLongitudeE6(int mLongitudeE6) {
//		this.mLongitudeE6 = mLongitudeE6;
//	}
//
//	/**
//	 * @return the mAltitude
//	 */
//	public int getAltitude() {
//		return mAltitude;
//	}
//
//	/**
//	 * @param mAltitude
//	 *            the mAltitude to set
//	 */
//	public void setAltitude(int mAltitude) {
//		this.mAltitude = mAltitude;
//	}
//
//	/**
//	 * 
//	 * @return
//	 */
//	public float getLocationAccuracy() {
//		return locationAccuracy;
//	}
//
//	/**
//	 * 
//	 * @param locationAccuracy
//	 */
//	public void setLocationAccuracy(float locationAccuracy) {
//		this.locationAccuracy = locationAccuracy;
//	}
//
//}
