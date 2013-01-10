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
package org.n52.android.utils;

/**
 * Provides access to main application logging framework. This allows to include
 * data source logging information into GeoAR error reports.
 */
public class DataSourceLoggerFactory {
	/**
	 * Used to get actual {@link Logger} implementation from main app
	 */
	public interface LoggerCallable {
		Logger call(Class<?> clazz);
	}

	/**
	 * Simple interface to log messages and {@link Throwable}s
	 */
	public interface Logger {
		void warn(String message);

		void error(String message);

		void info(String message);

		void warn(String message, Throwable ex);

		void error(String message, Throwable ex);

		void info(String message, Throwable ex);
	}

	protected static LoggerCallable loggerCallable;

	public static Logger getLogger(Class<?> clazz) {
		if (loggerCallable == null) {
			throw new RuntimeException("Logger not initialized");
		}
		return loggerCallable.call(clazz);
	}

	public static void setLoggerCallable(LoggerCallable loggerCallable) {
		DataSourceLoggerFactory.loggerCallable = loggerCallable;
	}

}
