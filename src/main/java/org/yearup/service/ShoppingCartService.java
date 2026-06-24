package org.yearup.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }





    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart

        List<CartItem> items = shoppingCartRepository.findByUserId(userId); //this has gotten me that specific row from user id

        //product id is what is in common between item and products
        ShoppingCart shoppingCart = new ShoppingCart();  //created an instance of shopping cart we will be adding items in it

        for(CartItem item: items){ //looping through each row
            Product product = productService.getById(item.getProductId());  //made an instance of product and got the product by product id

            ShoppingCartItem sci = new ShoppingCartItem();  //created an instance of shopping cart item

            sci.setProduct(product);   //added the product to it
            sci.setQuantity(item.getQuantity());  //added the quantity to it

            shoppingCart.add(sci);  //now added sci to the shopping cart

        }

        return shoppingCart; //returned the shopping cart

    }



    // add additional methods here


    //As a reminder:
    //
    //It should accept int userId and int productId.
    //
    //It uses your repository to look up an existing record by both IDs.
    //
    //It uses an if/else block to determine whether to create a brand new CartItem with a quantity of 1 or increment an existing CartItem's quantity.
    //
    //It finishes by returning the fully populated cart using your existing getByUserId(userId) method.

    public ShoppingCart addNewProductToShoppingCart(int userId, int productId){

        //Step 1: Check for an existing item in the database
        //Take the incoming userId and productId and pass them to your shopping cart repository. You are asking
        // the database: "Does a row already exist where this specific user has this specific product in their
        // cart?" Store the result of this search in a temporary variable (let's call it the "found item").
        //

        CartItem foundItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        //Step 2: Make a conditional decision (The If/Else check)
        //Look at the "found item" from Step 1 to decide which path to take:
        //Path A: If the item was found (It already exists)
        //Extract the current quantity number from that found item.
        //Add 1 to that number to increase it.
        //Update the found item with this new, higher quantity number.
        //Pass this updated item back to your shopping cart repository's save method so it updates the existing row in the database.
        if(foundItem != null){
            //add 1 to the quantity number here
            int quantityNum = foundItem.getQuantity();
            foundItem.setQuantity(quantityNum + 1);

            shoppingCartRepository.save(foundItem);
        }
        //Path B: If the item was NOT found (It's a brand new product for this user)
        //Create a brand new, empty cart item record.
        //Set its user ID to match the incoming userId.
        //Set its product ID to match the incoming productId.
        //Set its starting quantity to exactly 1.
        //Pass this brand-new record to your shopping cart repository's save method so it inserts a new row into the database.
        else{
            CartItem cartItem = new CartItem();
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setQuantity(1);

            shoppingCartRepository.save(cartItem);
        }


        //Step 3: Retrieve and return the final results
        //Now that the database has been successfully updated (either by updating an old row or inserting a new
        // one), call your existing service method getByUserId(userId). This will automatically fetch all the
        // user's items, look up their full product details, bundle them into the master ShoppingCart object,
        // and return it right back to the controller.

        return getByUserId(userId);

    }


    public ShoppingCart delete(int userId){
        shoppingCartRepository.deleteByUserId(userId);
        return new ShoppingCart();
    }


    public ShoppingCart updateProduct(int userId, int productId, int newQuantity) {
        CartItem foundItem = shoppingCartRepository.findByUserIdAndProductId(userId, productId);

        if (foundItem != null) {
            foundItem.setQuantity(newQuantity);
            shoppingCartRepository.save(foundItem);
        }
        return getByUserId(userId);
    }



}
