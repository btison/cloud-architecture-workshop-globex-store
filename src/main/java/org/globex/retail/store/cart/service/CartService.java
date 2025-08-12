package org.globex.retail.store.cart.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.globex.retail.store.cart.model.Cart;
import org.globex.retail.store.cart.model.CartItem;
import org.globex.retail.store.cart.store.CartStore;

@ApplicationScoped
public class CartService {

    @Inject
    CartStore cartStore;

    public Cart getCart(String cartId) {
        Cart cart = cartStore.getCart(cartId);
        if (cart == null) {
            cart = new Cart(cartId);
        }
        return cart;
    }

    public Cart addItemToCart(String cartId, CartItem item) {
        Cart cart = cartStore.getCart(cartId);
        if (cart == null) {
            cart = new Cart(cartId);
        }
        cart.addItem(item);
        cartStore.storeCart(cart);
        return cart;
    }

    public Cart removeItemFromCart(String cartId, CartItem item) {
        Cart cart = cartStore.getCart(cartId);
        if (cart == null) {
            cart = new Cart(cartId);
            return cart;
        }
        cart.removeItem(item);
        cartStore.storeCart(cart);
        return cart;
    }

    public Cart emptyCart(String cartId) {
        cartStore.removeCart(cartId);
        return new Cart(cartId);
    }

}
