package org.vicrul.liferay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.vicrul.liferay.dao.CategoryDAO;
import org.vicrul.liferay.model.Category;
import org.vicrul.liferay.util.ImportAPI;

import lombok.NoArgsConstructor;

@Transactional
@Service("CategoryService")
@NoArgsConstructor
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDAO categoryDao;

	private ImportAPI importAPI;

	@Override
	public List<Category> getAllCategories() {

		return categoryDao.getAllCategories();
	}

	@Override
	public void updateCategoryList() {
		
		if(!getAllCategories().isEmpty()) {
			categoryDao.removeAllCategories();
		}
		
		importAPI = new ImportAPI();
		List<Category> allCategories = importAPI.getCategories();
		categoryDao.saveCategory(allCategories);
	}

	@Override
	public Category getCategory(long categoryId) {
		return categoryDao.findCategory(categoryId);
	}

}
