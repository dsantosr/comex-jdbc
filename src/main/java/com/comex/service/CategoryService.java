package com.comex.service;

import com.comex.dao.CategoryDAO;
import com.comex.exception.EntityNotFoundException;
import com.comex.model.Category;

import java.util.List;

public class CategoryService {

    private CategoryDAO categoryDAO;

    public CategoryService() {
        this.categoryDAO = new CategoryDAO();
    }

    public void createCategory(Category newCategory) {
        System.out.println("Validando dados de categoria...");

        this.categoryDAO.create(newCategory);
    }

    public List<Category> listCategories() {
        return this.categoryDAO.listAll();
    }

    public void deleteCategoryByID(long id) throws EntityNotFoundException {
        Category categoryToDelete = categoryDAO.searchByID(id);
        if (categoryToDelete == null) {
            throw new EntityNotFoundException("Categoria não está cadastrada: " + id);
        }
        this.categoryDAO.delete(categoryToDelete);
    }

    public void updateCategory(Category category) {
        System.out.println("Validando dados da categoria...");

        this.categoryDAO.update(category);
    }
}