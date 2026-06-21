package org.yearup.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests


@RestController
@RequestMapping("/categories")
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;


    // create an Autowired constructor to inject the categoryService and productService

    public CategoriesController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
    }


    public CategoriesController(){}


    @GetMapping
    // add the appropriate annotation for a get action
    public List<Category> getAll()
    {
        // find and return all categories
        return categoryService.getAllCategories();
    }


    @GetMapping("/{id}")
    // add the appropriate annotation for a get action
    public Optional<Category> getById(@PathVariable int id)
    {
        // get the category by id
        return categoryService.getById(id);
    }


    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId
        return null;
    }




    //////////////////////////////////////////////////////////


    //only admins should be able to do this
    @PostMapping
    @PreAuthorize("hasRole(Admin)")
    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        // insert the category and return it with status 201 Created

        categoryService.create(category);
        return ResponseEntity.ok(category);
    }


    //only admins should be able to do this

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public Category updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id and return the updated category (200 OK)
        return null;
    }


    //only admins should be able to do this

    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        // delete the category by id and return status 204 No Content
        return null;
    }
}
