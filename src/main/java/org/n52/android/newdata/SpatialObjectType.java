package org.n52.android.newdata;

import android.graphics.Bitmap;
import android.location.Location;
import android.os.Parcel;

public class SpatialObjectType extends SpatialEntity {

	/**
	 * static constants
	 */
	private static final long serialVersionUID = 1L;

	/*********************
	 * Fields
	 *********************/
	private Bitmap mapIcon;
	private Bitmap arIcon;

	protected final String mUid;
	protected final String mTitle;
	protected final String mDescription;

	private int color;

	/**
	 * 
	 * @param title
	 *            this should be <b>singleLine</b> (no <code>'\n'</code> )
	 * @param description
	 *            a <b>multiLine</b> description ( <code>'\n'</code> possible)
	 * @param geoPoint
	 */
	public SpatialObjectType(final String uid, final String title,
			final String description, int latitudeE6, int longitudeE6,
			int altitude) {
		super(latitudeE6, longitudeE6, altitude);
		this.mUid = uid;
		this.mTitle = title;
		this.mDescription = description;
	}

	public void setMapIcon(String mapIconPath) {
		// TODO
	}

	public void setARIcon(String arIconPath) {
		// TODO
	}

	@Override
	protected void devicePositionUpdate(Location l) {
		this.distanceToDevice = distanceTo(l.getLatitude(), l.getLongitude());
	}

	/**
	 * @return the mapIcon
	 */
	public Bitmap getMapIcon() {
		return mapIcon;
	}

	/**
	 * @param mapIcon
	 *            the mapIcon to set
	 */
	public void setMapIcon(Bitmap mapIcon) {
		this.mapIcon = mapIcon;
	}

	/**
	 * @return the arIcon
	 */
	public Bitmap getArIcon() {
		return arIcon;
	}

	/**
	 * @param arIcon
	 *            the arIcon to set
	 */
	public void setArIcon(Bitmap arIcon) {
		this.arIcon = arIcon;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return mTitle;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return mDescription;
	}

	/**
	 * @return the mUid
	 */
	public String getUid() {
		return mUid;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}

}
