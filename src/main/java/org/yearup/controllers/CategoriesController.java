package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.authorization.AuthorityReactiveAuthorizationManager.hasRole;

// add the annotations to make this a REST controller
// add the annotation to make this controller the endpoint for the following url
    // http://localhost:8080/categories
// add annotation to allow cross site origin requests


@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;


    // create an Autowired constructor to inject the categoryService and productService

    @Autowired
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
    @PreAuthorize("permitAll()")
    // add the appropriate annotation for a get action
    public Category getById(@PathVariable int id)
    {
        // get the category by id
        Category category = categoryService.getById(id);

        if (category == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return category;
    }


    // the url to return all products in category 1 would look like this
    // https://localhost:8080/categories/1/products
    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        // get a list of product by categoryId



        return productService.listByCategoryId(categoryId);
    }




    //////////////////////////////////////////////////////////


    //only admins should be able to do this
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    // add annotation to call this method for a POST action
    // add annotation to ensure that only an ADMIN can call this function
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        // insert the category and return it with status 201 Created

        Category saved  = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    //only admins should be able to do this

    // add annotation to call this method for a PUT (update) action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category)
    {

        if(categoryService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        // update the category by id and return the updated category (200 OK)
        return categoryService.update(id, category);
    }


    //only admins should be able to do this

    // add annotation to call this method for a DELETE action - the url path must include the categoryId
    // add annotation to ensure that only an ADMIN can call this function
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        // delete the category by id and return status 204 No Content

        if(categoryService.getById(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
