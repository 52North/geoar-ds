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

import java.util.ArrayList;

import android.opengl.GLES20;

/**
 * Holds the information for the rendering process.
 * 
 * @author Arne de Wall
 * 
 */
public abstract class RenderDetails {
	
	public enum Shader{
		SimpleColorShader,
		PhongShader
	}

	/***********************
	 * Render Objectives
	 ***********************/
	public float[] vertices;
	public float[] colors;
	public float[] normals;
	public int[] indices;
	
	public float scaleFactor = 1.0f;

	/***********************
	 * Render Settings
	 ***********************/
	public int drawingMode = GLES20.GL_TRIANGLES;
	public boolean enableBlending = false;
	public boolean enableDepthTest = true;
	public boolean enableDepthMask = true;
	public boolean enableCullFace = false;
	public boolean isComposition = false;
	
	public Shader selectedShader = Shader.SimpleColorShader;
	public final ArrayList<RenderDetails> children = new ArrayList<RenderDetails>();


	/*******************************
	 * Constructor
	 *******************************/
	public RenderDetails(){}
	
	public RenderDetails(float[] vertices, float[] colors, float[] normals, int[] indices) {
		this.vertices = vertices;
		this.colors = colors;
		this.normals = normals;
		this.indices = indices;
	}
	
	/******************************
	 * Methods
	 ******************************/
	/**
	 * 
	 * @param vertices
	 * 				vertices array to set
	 * @param colors
	 * 				colors array to set
	 * @param normals
	 * 				normals array to set
	 * @param indices
	 * 				indices array to set
	 */
	protected void setRenderDetails(float[] vertices, float[] colors, float[] normals, int[] indices){
		this.vertices = vertices;
		this.colors = colors; 
		this.normals = normals;
		this.indices = indices;
	}
	
	public abstract void onPreRender();

}
