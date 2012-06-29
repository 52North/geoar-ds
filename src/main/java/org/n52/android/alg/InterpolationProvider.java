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

package org.n52.android.alg;

import java.util.List;

import org.n52.android.alg.proj.MercatorRect;
import org.n52.android.data.DataSourceAbstractFactory;
import org.n52.android.data.Measurement;

import android.graphics.Bitmap;

/**
 * 
 * @author Arne de Wall
 *
 */
public final class InterpolationProvider implements OnDatasourceChangedListener{
	
	private static DatasourceInterpolation interpolation;
	
	public interface DatasourceInterpolation {
		byte[] interpolate(List<Measurement> measurements,
				MercatorRect bounds, byte[] resInterpolation,
				OnProgressUpdateListener progressUpdateListener);
		Bitmap interpolationToBitmap(int width, int height,
				byte[] interpolationArray, Bitmap reuseBmp);
		Bitmap interpolationToBitmap(MercatorRect bounds,
				byte[] interpolationArray, Bitmap reuseBmp);
	}
	
	@Override
	public void onDatasourceChanged(DataSourceAbstractFactory newDatasource) {
		interpolation = newDatasource.getInterpolationProvider();
	}
	
	public static void OnDatasourceChanged(DataSourceAbstractFactory newDatasource){
		interpolation = newDatasource.getInterpolationProvider();
	}

	public static byte[] interpolate(List<Measurement> measurements,
			MercatorRect bounds, byte[] resInterpolation,
			OnProgressUpdateListener progressUpdateListener){
		return interpolation.interpolate(measurements, bounds, resInterpolation, progressUpdateListener);
	}

	public static Bitmap interpolationToBitmap(int width, int height,
			byte[] interpolationArray, Bitmap reuseBmp){
		return interpolation.interpolationToBitmap(width, height, interpolationArray, reuseBmp);
	}
	
	public static Bitmap interpolationToBitmap(MercatorRect bounds,
			byte[] interpolationArray, Bitmap reuseBmp) {
		return interpolation.interpolationToBitmap(bounds, interpolationArray, reuseBmp);
	}
	
	public static void setInterpolation(DatasourceInterpolation di){
		InterpolationProvider.interpolation = di;
	}
}
