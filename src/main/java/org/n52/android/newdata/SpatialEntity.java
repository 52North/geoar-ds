package org.n52.android.newdata;

import java.io.Serializable;

import android.location.Location;
import android.os.Parcelable;

/**
 * Holds the pose information and a geometrical representation to the super
 * class.
 * 
 * @author Arne de Wall
 * 
 */
public abstract class SpatialEntity implements LocationUpdateListener,
		Parcelable, Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	// ================================
	// Fields
	// ================================
	protected boolean isVisible;

	protected int mLatitudeE6; // latitude, measured in microdegrees (degrees * 1E6).
	protected int mLongitudeE6; // longitude, measured in microdegrees (degrees * 1E6).
	protected int mAltitude;

	protected float locationAccuracy;
	protected float distanceToDevice;

	protected float[] relPositionVec;

	// ================================
	// Constructors
	// =================================

	/**
	 * 
	 * @param latitudeE6
	 * @param longitudeE6
	 */
	public SpatialEntity(final int latitudeE6, final int longitudeE6) {
		this.mLatitudeE6 = latitudeE6;
		this.mLongitudeE6 = longitudeE6;
	}

	public SpatialEntity(final double latitude, final double longitude) {
		this.mLatitudeE6 = (int) (latitude * 1E6);
		this.mLongitudeE6 = (int) (longitude * 1E6);
	}

	public SpatialEntity(final int latitudeE6, final int longitudeE6,
			final int altitude) {
		this(latitudeE6, longitudeE6);
		this.mAltitude = altitude;
	}

	public SpatialEntity(final double latitude, final double longitude,
			final double altitude) {
		this(latitude, longitude);
		this.mAltitude = (int) altitude;
	}

	// ================================
	// Methods
	// ================================

	/**
	 * 
	 * @param otherLatitudeE6
	 *            Latitude value, measured in microdegrees (degrees * 1E6).
	 * @param otherLongitudeE6
	 *            Longitude, measured in microdegrees (degrees * 1E6).
	 * @return the distance in Meters
	 */
	public int distanceTo(final int otherLatitudeE6, final int otherLongitudeE6) {
		return distanceTo(otherLatitudeE6 / 1E6, otherLongitudeE6 / 1E6);
	}

	/**
	 * 
	 * @param otherLatitude
	 * @param otherLongitude
	 * @return
	 */
	public int distanceTo(final double otherLatitude,
			final double otherLongitude) {
		float[] res = new float[1];
		Location.distanceBetween(mLatitudeE6 / 1E6, mLongitudeE6 / 1E6,
				otherLatitude, otherLongitude, res);
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
		final double thisLatitude = mLatitudeE6 / 1E6;
		final double thisLongitude = mLongitudeE6 / 1E6;
		final double thisAltitude = mAltitude;

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

	@Override
	public void devicePositionUpdate(Location l, boolean changeViewToAR) {
		if (isVisible || changeViewToAR) {
			calculateRelativePositionVector(l.getLatitude(), l.getLongitude(),
					l.getAltitude());
			isVisible = true;
		}
	}

	protected abstract void devicePositionUpdate(Location l);

	// ================================
	// Getter / Setter
	// ================================

	/**
	 * @return the mLatitudeE6
	 */
	public int getmLatitudeE6() {
		return mLatitudeE6;
	}

	/**
	 * @return Latitude not in microdegrees
	 */
	public double getLatitude() {
		return mLatitudeE6 / 1E6;
	}

	/**
	 * @param mLatitudeE6
	 *            the mLatitudeE6 to set
	 */
	public void setmLatitudeE6(int mLatitudeE6) {
		this.mLatitudeE6 = mLatitudeE6;
	}

	/**
	 * @return the mLongitudeE6
	 */
	public int getmLongitudeE6() {
		return mLongitudeE6;
	}

	/**
	 * @return Longitude not in microdegrees
	 */
	public double getmLongitude() {
		return mLongitudeE6 / 1E6;
	}

	/**
	 * @param mLongitudeE6
	 *            the mLongitudeE6 to set
	 */
	public void setmLongitudeE6(int mLongitudeE6) {
		this.mLongitudeE6 = mLongitudeE6;
	}

	/**
	 * @return the mAltitude
	 */
	public int getmAltitude() {
		return mAltitude;
	}

	/**
	 * @param mAltitude
	 *            the mAltitude to set
	 */
	public void setmAltitude(int mAltitude) {
		this.mAltitude = mAltitude;
	}

}