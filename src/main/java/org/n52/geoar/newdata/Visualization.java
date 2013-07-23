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

import org.n52.geoar.newdata.vis.DataSourceVisualization.DataSourceVisualizationCanvas;
import org.n52.geoar.newdata.vis.DataSourceVisualization.DataSourceVisualizationGL;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;

import com.vividsolutions.jts.geom.Geometry;

public interface Visualization {

    interface FeatureVisualization {
        String getTitle(SpatialEntity2<? extends Geometry> entity);

        String getDescription(SpatialEntity2<? extends Geometry> entity);

        View getFeatureView(SpatialEntity2<? extends Geometry> entity,
                View convertView, ViewGroup parentView, Context activityContext);

        View getFeatureDetailView(SpatialEntity2<? extends Geometry> entity,
                View convertView, ViewGroup parentView, Context activityContext);
    }

    public interface MapVisualization extends Visualization {

        public interface ItemVisualization extends MapVisualization,
                FeatureVisualization {
            Drawable getDrawableForEntity(
                    SpatialEntity2<? extends Geometry> entity);
            // TODO change to geographic feature stuff

        }

        public interface GeometryVisualization extends MapVisualization,
                FeatureVisualization {
            Paint getOutlinePaint(SpatialEntity2<? extends Geometry> entity);

            Paint getStrokePaint(SpatialEntity2<? extends Geometry> entity);
        }

        public interface RasterVisualization extends MapVisualization {
            // TODO
        }
    }

    public interface ARVisualization extends Visualization {
        public interface ItemVisualization extends ARVisualization,
                FeatureVisualization {
            DataSourceVisualizationGL getEntityVisualization(
                    SpatialEntity2<? extends Geometry> entity,
                    RenderFeatureFactory fac);

            DataSourceVisualizationCanvas getEntityVisualization(
                    SpatialEntity2<? extends Geometry> entity);

        }

        public interface RasterVisualization extends ARVisualization {
            // TODO
        }

    }

}
