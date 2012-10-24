package org.n52.android.newdata;

import android.location.Location;

public interface LocationUpdateListener {
    void devicePositionUpdate(Location l, boolean changeViewToAR);
}
