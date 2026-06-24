package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.CartItem;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;


@RestController
@CrossOrigin
@RequestMapping("/cart")
// convert this class to a REST controller
// only logged in users should have access to these actions
public class ShoppingCartController {
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;

    @Autowired
    public ShoppingCartController(ShoppingCartService shoppingCartService, UserService userService) {
        this.shoppingCartService = shoppingCartService;
        this.userService = userService;
    }


    @GetMapping
    // each method in this controller requires a Principal object as a parameter
    public ShoppingCart getCart(Principal principal) {

        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use the shoppingCartService to get all items in the cart and return the cart


        return shoppingCartService.getByUserId(userId);
    }


    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created

    @PostMapping("/products/{productId}")
    public ResponseEntity<ShoppingCart> addNewProductToShoppingCart(@PathVariable int productId, Principal principal) {
        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        ShoppingCart saved = shoppingCartService.addNewProductToShoppingCart(userId, productId);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)

    @DeleteMapping
    public ShoppingCart delete(Principal principal){
        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return shoppingCartService.delete(userId);
    }




    @PutMapping("/products/{productId}")
    public ResponseEntity<ShoppingCart> updateProduct(@PathVariable int productId, Principal principal, @RequestBody CartItem cartItem){
        String userName = principal.getName();
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        int newQuantity = cartItem.getQuantity();

        ShoppingCart saved = shoppingCartService.updateProduct(userId, productId, newQuantity);


        return ResponseEntity.ok(saved);


    }


}
