package org.vicrul.liferay.dao;

import java.util.List;

import org.vicrul.liferay.model.Region;

public interface RegionDAO {
	
	List<Region> getAllRegions();
	void saveRegions(List<Region> allRegions);
	void removeAllRegions();
	Region findRegion(long regionId);
}
