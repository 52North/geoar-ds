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
package org.n52.geoar.alg.proj;

import org.n52.geoar.newdata.SpatialEntity2;

import com.vividsolutions.jts.geom.Geometry;


/**
 * Class to hold bounds in {@link MercatorProj} coordinates
 * 
 * @author Holger Hopmann
 * 
 */
public class MercatorRect {
	public int top, left, bottom, right;
	public byte zoom;

	public MercatorRect(int left, int top, int right, int bottom, byte zoom) {
		this.left = left;
		this.top = top;
		this.bottom = bottom;
		this.right = right;
		this.zoom = zoom;
	}

	public MercatorRect(float left, float top, float right, float bottom,
			byte zoom) {
		this.left = (int) left;
		this.top = (int) top;
		this.bottom = (int) bottom;
		this.right = (int) right;
		this.zoom = zoom;
	}

	public int width() {
		return right - left;
	}

	public int height() {
		return bottom - top;
	}

	public MercatorPoint getCenter() {
		return new MercatorPoint((int) (left + width() / 2f),
				(int) (top + height() / 2f), zoom);
	}

	public void expand(int dx, int dy) {
		left -= dx;
		right += dx;
		top -= dy;
		bottom += dy;
	}

	public boolean contains(MercatorRect newBounds) {
		MercatorRect otherRect = newBounds.transform(zoom);

		return left <= otherRect.left && right >= otherRect.right
				&& top <= otherRect.top && bottom >= otherRect.bottom;
	}

	public MercatorRect transform(byte dstZoom) {
		return new MercatorRect((int) MercatorProj.transformPixel(left, zoom,
				dstZoom),
				(int) MercatorProj.transformPixel(top, zoom, dstZoom),
				(int) MercatorProj.transformPixel(right, zoom, dstZoom),
				(int) MercatorProj.transformPixel(bottom, zoom, dstZoom),
				dstZoom);
	}

	public boolean contains(SpatialEntity2<? extends Geometry> entity) {
		float leftLon = (float) MercatorProj.transformPixelXToLon(left, zoom);
		float rightLon = (float) MercatorProj.transformPixelXToLon(right, zoom);
		float topLat = (float) MercatorProj.transformPixelYToLat(top, zoom);
		float bottomLat = (float) MercatorProj.transformPixelYToLat(bottom,
				zoom);

		return entity.getLatitude() <= topLat
				&& entity.getLatitude() >= bottomLat
				&& entity.getLongitude() >= leftLon
				&& entity.getLongitude() <= rightLon;
	}

}