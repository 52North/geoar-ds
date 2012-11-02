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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.n52.android.alg.proj.MercatorProj;

public interface Annotations {

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface DataSource {

		/**
		 * The human readable name of a datasource
		 * 
		 * @return
		 */
		String name();

		/**
		 * Describes a datasource
		 * 
		 * @return
		 */
		String description() default "";

		/**
		 * Gets the {@link MercatorProj} zoom to use for the spatial index of
		 * the {@link MeasurementManager}
		 * 
		 * @return
		 */
		byte preferredZoomLevel() default 10;

		/**
		 * Gets the interval after which data has to get rerequested
		 * 
		 * @return
		 */
		long minReloadInterval() default -1;
	}

	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public @interface SupportedVisualization {

		Class<? extends Visualization>[] visualizationClasses();
	}
	
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.FIELD)
	public @interface Filterable {
		String value();
	}
	
	

}
