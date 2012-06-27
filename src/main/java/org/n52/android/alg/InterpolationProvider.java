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
