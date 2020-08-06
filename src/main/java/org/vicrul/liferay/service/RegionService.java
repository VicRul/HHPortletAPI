package org.vicrul.liferay.service;

import java.util.List;

import org.vicrul.liferay.model.Region;

public interface RegionService {

	List<Region> getAllRegions();
	void updateRegionList();
}
