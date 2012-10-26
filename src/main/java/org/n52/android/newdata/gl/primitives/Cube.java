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
package org.n52.android.newdata.gl.primitives;

import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.n52.android.newdata.gl.RenderDetails;

import android.graphics.Color;
import android.opengl.GLES20;

public class Cube extends RenderDetails{
		
	private static final float[] VERTICES ={
        0.5f, 0.5f, 0.5f, -0.5f, 0.5f, 0.5f, -0.5f,-0.5f, 0.5f, 0.5f,-0.5f, 0.5f, 	//0-1-halfSize-3 front
        0.5f, 0.5f, 0.5f, 0.5f,-0.5f, 0.5f,  0.5f,-0.5f,-0.5f, 0.5f, 0.5f,-0.5f,	//0-3-4-5 right
        0.5f,-0.5f,-0.5f, -0.5f,-0.5f,-0.5f, -0.5f, 0.5f,-0.5f, 0.5f, 0.5f,-0.5f,	//4-7-6-5 back
        -0.5f, 0.5f, 0.5f, -0.5f, 0.5f,-0.5f, -0.5f,-0.5f,-0.5f, -0.5f,-0.5f, 0.5f,	//1-6-7-halfSize left
        0.5f, 0.5f, 0.5f, 0.5f, 0.5f,-0.5f, -0.5f, 0.5f,-0.5f, -0.5f, 0.5f, 0.5f, 	//top
        0.5f,-0.5f, 0.5f, -0.5f,-0.5f, 0.5f, -0.5f,-0.5f,-0.5f, 0.5f,-0.5f,-0.5f,	//bottom
};
	
	private static final float[] COLORS = {
            1, 1, 1, 1, 	1, 1, 1, 1,	1, 1, 1, 1,	1, 1, 1, 1,
            1, 1, 1, 1,		1, 1, 1, 1,	1, 1, 1, 1, 	1, 1, 1, 1,
            1, 1, 1, 1, 	1, 1, 1, 1,	1, 1, 1, 1,	1, 1, 1, 1,
            1, 1, 1, 1,		1, 1, 1, 1,	1, 1, 1, 1, 	1, 1, 1, 1,
            1, 1, 1, 1, 	1, 1, 1, 1,	1, 1, 1, 1,	1, 1, 1, 1,
            1, 1, 1, 1,		1, 1, 1, 1,	1, 1, 1, 1, 	1, 1, 1, 1
	};
	
	private static final float[] NORMALS = {
            0, 0, 1,   0, 0, 1,   0, 0, 1,   0, 0, 1,     //front
            1, 0, 0,   1, 0, 0,   1, 0, 0,   1, 0, 0,     // right
            0, 0,-1,   0, 0,-1,   0, 0,-1,   0, 0,-1,     //back
            -1, 0, 0,  -1, 0, 0,  -1, 0, 0,  -1, 0, 0,     // left
            0, 1, 0,   0, 1, 0,   0, 1, 0,   0, 1, 0,     //  top                          
            0,-1, 0,   0,-1, 0,   0,-1, 0,   0,-1, 0,     // bottom
	};
	
	private static final int[] INDICES = {
            0,1,2, 	0,2,3,
            2,1,0, 	2,3,0,
            4,5,6, 	4,6,7,
            8,9,10, 	8,10,11,
            12,13,14, 	12,14,15,
            16,17,18, 	16,18,19,
            20,21,22, 	20,22,23,
	};

	public Cube(float size){
		this.scaleFactor = size;
		setRenderDetails(VERTICES, COLORS, NORMALS, INDICES);
		drawingMode = GLES20.GL_TRIANGLES;
	}
	
	public Cube(float size, int color, Float alpha){
		/** color details */
		float r = Color.red(color);
		float g = Color.green(color);
		float b = Color.blue(color);
		float a = alpha != null ? alpha : Color.alpha(color);
		float[] colors = {
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,
        		r, g, b, a, 	r, g, b, a,	r, g, b, a,	r, g, b, a,			
		};
		/** set data  */
		this.scaleFactor = size;
		this.drawingMode = GLES20.GL_TRIANGLES;
		setRenderDetails(VERTICES, colors, NORMALS, INDICES);
	}

	private void init(float size, float[] colors){
		this.vertices = VERTICES;
		this.colors = colors;
		this.indices = INDICES;
		drawingMode = GLES20.GL_TRIANGLES;
	}
	
	@Override
	public void onPreRender() {
		GLES20.glDisable(GLES20.GL_CULL_FACE);
	}
}
