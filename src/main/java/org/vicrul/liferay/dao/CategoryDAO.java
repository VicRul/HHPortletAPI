package org.vicrul.liferay.dao;

import java.util.List;

import org.vicrul.liferay.model.Category;

public interface CategoryDAO {
	
	List<Category> getAllCategories();
	void saveCategory(List<Category> allCategories);
	void removeAllCategories();
	Category findCategory(long categoryId);
}
