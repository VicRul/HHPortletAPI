package org.vicrul.liferay.service;

import java.util.List;

import org.vicrul.liferay.model.Category;

public interface CategoryService {

	List<Category> getAllCategories();
	void updateCategoryList();
	Category getCategory(long categoryId);
}
