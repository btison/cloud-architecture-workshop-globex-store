package org.globex.retail.store.cart.rest;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.globex.retail.store.cart.model.CartItem;
import org.globex.retail.store.cart.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Path("/services/cart")
public class CartResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CartResource.class);

    @Inject
    CartService cartService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cartId}")
    public Uni<Response> getCart(@PathParam("cartId") String cartId) {
        return Uni.createFrom().item(() -> cartId)
                .onItem().transform(id -> cartService.getCart(id))
                .onItem().transform(cart -> Response.ok(cart).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching cart", throwable);
                    return Response.serverError().build();
                });
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cartId}")
    public Uni<Response> addItemToCart(@PathParam("cartId") String cartId, CartItem cartItem) {
        if (cartItem == null) {
            return Uni.createFrom().item(Response.status(Response.Status.NO_CONTENT).build());
        }
        return Uni.createFrom().item(() -> null)
                .onItem().transform(o -> cartService.addItemToCart(cartId, cartItem))
                .onItem().transform(cart -> Response.ok(cart).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while adding item to cart", throwable);
                    return Response.serverError().build();
                });
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{cartId}")
    public Uni<Response> removeItemFromCart(@PathParam("cartId") String cartId, CartItem cartItem) {
        if (cartItem == null) {
            return Uni.createFrom().item(Response.status(Response.Status.NO_CONTENT).build());
        }
        return Uni.createFrom().item(() -> null)
                .onItem().transform(o -> cartService.removeItemFromCart(cartId, cartItem))
                .onItem().transform(cart -> Response.ok(cart).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while removing item from cart", throwable);
                    return Response.serverError().build();
                });
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/empty/{cartId}")
    public Uni<Response> emptyCart(@PathParam("cartId") String cartId) {
        return Uni.createFrom().item(() -> null)
                .onItem().transform(o -> cartService.emptyCart(cartId))
                .onItem().transform(cart -> Response.ok(cart).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while emptying cart", throwable);
                    return Response.serverError().build();
                });
    }
}
