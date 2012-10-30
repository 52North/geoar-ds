package org.n52.android.newdata;

import android.location.Location;
import android.os.Parcel;

/**
 * 
 * @author Arne de Wall
 *
 */
public final class SpatialObservation<E extends Number> extends SpatialEntity {

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
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void devicePositionUpdate(Location l) {
		this.distanceToDevice = distanceTo(l.getLatitude(), l.getLongitude());
	}

}
