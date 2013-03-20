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

import android.graphics.Bitmap;

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

}
