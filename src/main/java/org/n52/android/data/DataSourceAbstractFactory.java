package org.n52.android.data;

import org.n52.android.alg.InterpolationProvider.DatasourceInterpolation;

import android.graphics.Bitmap;

/**
 * 
 * @author Arne de Wall
 * 
 */
public interface DataSourceAbstractFactory {
	public String getDatasourceName();

	public Measurement createMeasurement();

	public MeasurementManager createMeasurementManager();

	public DatasourceInterpolation getInterpolationProvider();
}
