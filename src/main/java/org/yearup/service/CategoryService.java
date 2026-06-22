package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.repository.CategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }


    /**
     *
     * This Method will be returning all of the categories.
     * @return
     */
    public List<Category> getAllCategories()
    {
        // get all categories
        return categoryRepository.findAll();
    }

    /**
     * this will return the category based on the type of id that will be given to it.
     * @param categoryId
     * @return
     */
    public Category getById(int categoryId)
    {
        // get category by id
        return categoryRepository.findById(categoryId).orElse(null);
    }








    ///////////////////////////////////////////////////////////////
    public Category create(Category category)
    {
        // create a new category
        return categoryRepository.save(category);
    }



    //figure out how to update something
    public Category update(int categoryId, Category category)
    {
        // update category and return the updated category


//        category.setCategoryId(categoryId);
        Category existing = categoryRepository.findById(categoryId).orElseThrow();

        existing.setCategoryId(category.getCategoryId());
        existing.setName(category.getName());
        existing.setDescription(category.getDescription());

        return categoryRepository.save(existing);
    }



    public void delete(int categoryId)
    {
        // delete category

        categoryRepository.deleteById(categoryId);
    }
}
