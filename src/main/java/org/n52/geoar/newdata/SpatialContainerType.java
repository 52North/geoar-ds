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

import java.util.ArrayList;
import java.util.List;

import com.vividsolutions.jts.geom.Geometry;

/**
 * 
 * @author Arne de Wall <a.dewall@52North.org>
 * 
 * @param <G>
 * @param <E>
 */
public abstract class SpatialContainerType<G extends Geometry, E extends Number>
        extends SpatialEntity2<G> {

    /**
     * Constatants
     */
    private static final long serialVersionUID = 1L;

    public SpatialContainerType(G geometry){
        super(geometry);
    }
    
    private List<SpatialObservation<G, E>> observations = new ArrayList<SpatialObservation<G, E>>();

    public void addObservation(G geometry, E value) {
        if (observations != null) {
            SpatialObservation<G, E> observation = new SpatialObservation<G, E>(
                    geometry, value);
            addObservation(observation);
        }
    }

    public void addObservation(SpatialObservation<G, E> observation) {
        if (observations == null && observations.contains(observation))
            return;
        observations.add(observation);
    }

    public void getFeaturedVisualizations() {

    }
}
