package com.diplomska.backend.service.interfaces;

import com.diplomska.backend.helpers.LocationHelper;
import com.diplomska.backend.model.Location;

public interface LocationConverterService {
    LocationHelper getAsLocationHelper(Location location, String email);

}
