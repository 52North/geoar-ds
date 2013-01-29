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
package org.n52.android.newdata.vis;

import java.util.concurrent.Callable;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * 
 * @author Arne de Wall
 *
 */
public interface DataSourceVisualization {
	
	public interface DataSourceVisualizationGL extends DataSourceVisualization{
		
		public interface ItemRenderable extends DataSourceVisualizationGL{
			public int getColor();
			public float getScaleFactor();
		}

		public interface AreaRenderable extends DataSourceVisualizationGL{
			public byte[] getBitmapArray();
		}
		
		public interface TextureCallback {
			public Bitmap getTexture();
		}

		public void setOpenGLPreRenderingSettings();
		public void setColor(int androidColor);
		public void setColor(float[] colorArray);
		public void enableCullface(boolean cullface);
		public void enableBlending(boolean blending, float alpha);
		public void enableDepthtest(boolean depthTest);
		
		public void setTextureCallback(Callable<Bitmap> callback);
		
		public void setDrawingMode(int drawingMode);
	}
	
	public interface DataSourceVisualizationCanvas extends DataSourceVisualization{
		public void onRender(float cx, float cy, Canvas canvas);
	}

}
