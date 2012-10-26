package org.n52.android.newdata.gl;

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
