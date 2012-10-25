/**
 * Copyright 2012 52�North Initiative for Geospatial Open Source Software GmbH
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

package org.n52.android.data;

import java.lang.ref.SoftReference;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.n52.android.alg.InterpolationProvider.DatasourceInterpolation;
import org.n52.android.alg.OnProgressUpdateListener;
import org.n52.android.alg.proj.MercatorProj;
import org.n52.android.alg.proj.MercatorRect;
import org.n52.android.data.DataSource.RequestException;

import android.util.Log;

/**
 * Interface to request measurements from a specific {@link DataSource}. Builds
 * an automatic tile based cache of measurements to reduce data transfer. Update
 * interval of cached tiles is controlled by
 * , cache gets freed as required by the VM
 * 
 * @author Holger Hopmann
 * @author Arne de Wall
 */
public class MeasurementManager {

	public static final int ABORT_UNKOWN = 0;
	public static final int ABORT_NO_CONNECTION = 1;
	public static final int ABORT_CANCELED = 2;
	public static final int STEP_REQUEST = 3;
	
	
	public interface RequestHolder {
		void cancel();
	}

	public interface GetMeasurementsCallback {
		void onReceiveMeasurements(MeasurementTile measurements);
		void onAbort(MeasurementTile measurements, int reason);
	}
	
	public abstract interface GetMeasurementBoundsCallback extends
					OnProgressUpdateListener {
		void onReceiveDataUpdate(MercatorRect bounds, MeasurementsCallback measureCallback);
		void onAbort(MercatorRect bounds, int reason);
	}
	
	public class MeasurementsCallback{
		public List<Measurement> measurementBuffer;
		public byte[] interpolationBuffer;
	}

	public class MeasurementTile {
		public Tile tile;
		public long lastUpdate;
		public boolean updatePending;
		public Runnable fetchRunnable;
		public List<Measurement> measurements;

		protected List<GetMeasurementsCallback> callbacksMeasurement = new ArrayList<GetMeasurementsCallback>();

		public void addCallback(GetMeasurementsCallback callback) {
			synchronized (callbacksMeasurement) {
				callbacksMeasurement.add(callback);
			}
		}

		public void setMeasurements(List<Measurement> measurements) {
			lastUpdate = System.currentTimeMillis();
			this.measurements = measurements;

			synchronized (callbacksMeasurement) {
				updatePending = false;
				for (GetMeasurementsCallback callback : callbacksMeasurement) {
					callback.onReceiveMeasurements(this);
				}
				callbacksMeasurement.clear();
			}
		}

		public void abort(int reason) {
			synchronized (callbacksMeasurement) {
				updatePending = false;
				for (GetMeasurementsCallback callback : callbacksMeasurement) {
					callback.onAbort(this, reason);
				}
				callbacksMeasurement.clear();
			}
		}

		public MeasurementTile(Tile tile) {
			this.tile = tile;
		}

		public boolean hasMeasurements() {
			return measurements != null;
		}

	}
	
	
	protected Map<Tile, SoftReference<MeasurementTile>> tileCacheMapping = new HashMap<Tile, SoftReference<MeasurementTile>>();
	protected DataSource dataSource;
	protected DatasourceInterpolation interpolationProvider = null;
	protected ThreadPoolExecutor downloadThreadPool;
	protected byte tileZoom = 14;
	protected MeasurementFilter measurementFilter;

	public MeasurementManager(DataSource dataSource) {
		this.dataSource = dataSource;
		tileZoom = dataSource.getPreferredRequestZoom();
		measurementFilter = new MeasurementFilter();//DataSourceAdapter.createMeasurementFilter();// new MeasurementFilter();
		downloadThreadPool = (ThreadPoolExecutor) Executors
				.newFixedThreadPool(3);
	}
	
	public MeasurementManager(DataSource dataSource, DatasourceInterpolation interpolationProvider){
		this(dataSource);
		this.interpolationProvider = interpolationProvider;
	}

	public void setMeasurementFilter(MeasurementFilter measurementFilter) {
		clearCache();
		this.measurementFilter = measurementFilter;
	}

	public MeasurementFilter getMeasurementFilter() {
		return measurementFilter;
	}

	/**
	 * Cancels all operations on current measurements and clears the cache
	 */
	private void clearCache() {
		synchronized (tileCacheMapping) {
			for (SoftReference<MeasurementTile> cacheReference : tileCacheMapping
					.values()) {
				if (cacheReference.get() != null) {
					cacheReference.get().abort(ABORT_CANCELED);
				}
			}
			tileCacheMapping.clear();
		}
	}

	public RequestHolder getMeasurementsByTile(Tile tile,
			GetMeasurementsCallback callback, boolean forceUpdate) {
		Log.i("Test", "getMeasures " + tile.x + ", " + tile.y);

		RequestHolder requestHolder = null;

		synchronized (tileCacheMapping) {
			SoftReference<MeasurementTile> cacheReference = tileCacheMapping
					.get(tile);
			MeasurementTile tileCache = cacheReference != null ? cacheReference
					.get() : null;
			if (tileCache != null) {
				// Tile bereits vorhanden
				if (tileCache.hasMeasurements()) {
					callback.onReceiveMeasurements(tileCache);
				}
				if (!tileCache.hasMeasurements()
						|| tileCache.lastUpdate <= System.currentTimeMillis()
								- dataSource.getDataReloadMinInterval()
						|| forceUpdate) {
					tileCache.addCallback(callback);
					requestHolder = fetchMeasurements(tileCache);
				}
			} else {
				tileCache = new MeasurementTile(tile);
				tileCache.addCallback(callback);
				tileCacheMapping.put(tile, new SoftReference<MeasurementTile>(
						tileCache));

				requestHolder = fetchMeasurements(tileCache);
			}
		}

		return requestHolder;
	}

	private RequestHolder fetchMeasurements(final MeasurementTile tileCache) {

		if (!tileCache.updatePending) {

			tileCache.updatePending = true;
			tileCache.fetchRunnable = new Runnable() {
				public void run() {
					try {
						tileCache.setMeasurements(dataSource.getMeasurements(
								tileCache.tile, measurementFilter));
					} catch (RequestException e) {
						Log.i("NoiseAR", "RequestException" + e.getMessage());
						tileCache.abort(ABORT_UNKOWN);
					} catch (ConnectException e) {
						tileCache.abort(ABORT_NO_CONNECTION);
					}
				}
			};
			downloadThreadPool.execute(tileCache.fetchRunnable);
		}

		return new RequestHolder() {
			public void cancel() {
				downloadThreadPool.remove(tileCache.fetchRunnable);
			}
		};
	}

	public RequestHolder getMeasurementCallback(final MercatorRect bounds,
			final GetMeasurementBoundsCallback callback, boolean forceUpdate,
			MeasurementsCallback dataCallback) {
		// Kachelgrenzen
		final int tileLeftX = (int) MercatorProj.transformPixelXToTileX(
				MercatorProj.transformPixel(bounds.left, bounds.zoom, tileZoom),
				tileZoom);
		final int tileTopY = (int) MercatorProj.transformPixelYToTileY(
				MercatorProj.transformPixel(bounds.top, bounds.zoom, tileZoom),
				tileZoom);
		int tileRightX = (int) MercatorProj.transformPixelXToTileX(
				MercatorProj.transformPixel(bounds.right, bounds.zoom, tileZoom),
				tileZoom);
		int tileBottomY = (int) MercatorProj.transformPixelYToTileY(
				MercatorProj.transformPixel(bounds.bottom, bounds.zoom, tileZoom),
				tileZoom);
		final int tileGridWidth = tileRightX - tileLeftX + 1;

		// Liste zum Nachverfolgen der erhaltenen Messungen
		final Vector<List<Measurement>> tileMeasurementsList = new Vector<List<Measurement>>();
		tileMeasurementsList.setSize(tileGridWidth
				* (tileBottomY - tileTopY + 1));

		// Callback für die Messungen
		final GetMeasurementsCallback measureCallback = new GetMeasurementsCallback() {
			boolean active = true;
			private int progress;
			
			
			public void onReceiveMeasurements(MeasurementTile measurements) {
				
				final MeasurementsCallback res = new MeasurementsCallback();
				
				if (!active) {
					return;
				}

				int checkIndex = ((measurements.tile.y - tileTopY) * tileGridWidth)
						+ (measurements.tile.x - tileLeftX);

				if (tileMeasurementsList.set(checkIndex,
						measurements.measurements) == null) {
					// Previously no elements for that tile
					progress++;
					callback.onProgressUpdate(progress,
							tileMeasurementsList.size(), STEP_REQUEST);
				}
				if (!tileMeasurementsList.contains(null)) {
					// All tiles loaded
					final List<Measurement> measurementsList = new ArrayList<Measurement>();
					for (List<Measurement> tileMeasurements : tileMeasurementsList) {
						measurementsList.addAll(tileMeasurements);
					}
					
					res.measurementBuffer = measurementsList;
					
					new Thread(new Runnable() {
						public void run() {
							if (active) {
								if(interpolationProvider != null)
									res.interpolationBuffer = interpolationProvider
										.interpolate(measurementsList, bounds,
												res.interpolationBuffer, callback);
								callback.onReceiveDataUpdate(bounds,
										res);
							}
						}
					}).run();
				}
			}

			public void onAbort(MeasurementTile measurements, int reason) {
				callback.onAbort(bounds, reason);
				if (reason == ABORT_CANCELED) {
					active = false;
				}
			}
		};

		// Messungen besorgen
		for (int y = tileTopY; y <= tileBottomY; y++)
			for (int x = tileLeftX; x <= tileRightX; x++) {
				Tile tile = new Tile(x, y, tileZoom);
				
				getMeasurementsByTile(tile, measureCallback, forceUpdate);
			}

		return new RequestHolder() {
			public void cancel() {
				measureCallback.onAbort(null, ABORT_CANCELED);
			}
		};
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource selectedSource) {
		clearCache();
		tileZoom = dataSource.getPreferredRequestZoom();
		dataSource = selectedSource;
	}


}
