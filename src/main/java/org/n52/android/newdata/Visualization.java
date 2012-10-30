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
package org.n52.android.newdata;

import org.n52.android.newdata.gl.primitives.Renderable;

import android.graphics.drawable.Drawable;
import android.opengl.GLES20;

public interface Visualization {
	
	public enum VisualizationType{
		Cube(GLES20.GL_TRIANGLES);
		
		VisualizationType(int glDrawable){
			
		}
	}
	
	interface FeatureVisualization {
		String getTitle(SpatialEntity entity);

		String getDescription(SpatialEntity entity);
	}

	public interface MapVisualization extends Visualization {

		public interface ItemVisualization extends MapVisualization,
				FeatureVisualization {
			Drawable getDrawableForEntity(SpatialEntity entity);
			// TODO change to geographic feature stuff

		}

		public interface RasterVisualization extends MapVisualization {
			// TODO
		}
	}

	public interface ARVisualization extends Visualization {
		public interface ItemVisualization extends ARVisualization,
				FeatureVisualization {
			Renderable getEntityVisualization(SpatialEntity entity);

		}

		public interface RasterVisualization extends ARVisualization {
			// TODO
		}

	}

}
