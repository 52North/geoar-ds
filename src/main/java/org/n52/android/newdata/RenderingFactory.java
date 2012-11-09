package org.n52.android.newdata;

import org.n52.android.newdata.gl.primitives.RenderLoader;
import org.n52.android.newdata.gl.primitives.DataSourceRenderable;

public interface RenderingFactory {

	public DataSourceRenderable createCube();

	public DataSourceRenderable createSphere();

	public DataSourceRenderable createRenderable(RenderLoader renderLoader);

}