package org.n52.android.newdata.gl.primitives;

/**
 * 
 * @author Peter
 *
 */
public interface Renderable {

	public void onPreRender();
	
	public interface ItemRenderable extends Renderable{
		public int getColor();
		public float getScaleFactor();
	}

	public interface AreaRenderable extends Renderable{
		public byte[] getBitmapArray();
	}
}
