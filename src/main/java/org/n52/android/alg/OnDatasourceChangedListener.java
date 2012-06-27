package org.n52.android.alg;

import org.n52.android.data.DataSourceAbstractFactory;

public interface OnDatasourceChangedListener {
	void onDatasourceChanged(DataSourceAbstractFactory newDatasource);
}
