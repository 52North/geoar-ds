package org.n52.android.utils;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Holding Geolocation information.
 * @author Arne de Wall
 *
 */
public class GeoLocation implements Serializable, Parcelable {
	
	/***********************
	 * Statics
	 **********************/
	private static final long serialVersionUID = 1L;
	
	/***********************
	 * Variables
	 **********************/
	public int mLatitudeE6;
	public int mLongitudeE6;
	public int mAltitude;
	
	/***********************
	 * Constructors
	 **********************/
	public GeoLocation(final int latitudeE6, final int longitudeE6, final int altitude) {
		this.mLatitudeE6 = latitudeE6; 
		this.mLongitudeE6 = longitudeE6;
		this.mAltitude = altitude;
	}
	
	public GeoLocation(final int latitudeE6, final int longitudeE6){
		this(latitudeE6, longitudeE6, 0);
	}
	
	public GeoLocation(final double latitude, final double longitude){
		this((int)(latitude * 1E6), (int)(longitude * 1E6), 0);
	}
	
	public GeoLocation(final double latitude, final double longitude, final int altitude){
		this((int)(latitude * 1E6), (int)(longitude * 1E6), altitude);
	}
	
	/**
	 * 
	 * @param in
	 */
	public GeoLocation(Parcel in){
		this.mLatitudeE6 = in.readInt();
		this.mLongitudeE6 = in.readInt();
		this.mAltitude = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(mLatitudeE6);
		out.writeInt(mLongitudeE6);
		out.writeInt(mAltitude);
	}
	
	public static final Parcelable.Creator<GeoLocation> CREATOR = new Parcelable.Creator<GeoLocation>() {
		@Override
		public GeoLocation createFromParcel(final Parcel in) {
			return new GeoLocation(in);
		}

		@Override
		public GeoLocation[] newArray(final int size) {
			return new GeoLocation[size];
		}
	};
}
