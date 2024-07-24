package org.globex.retail.store.order.rest;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import io.vertx.core.json.JsonObject;
import org.globex.retail.store.order.model.dto.OrderDto;
import org.globex.retail.store.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("services/order")
public class OrderResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderResource.class);

    @Inject
    OrderService orderService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> storeOrder(OrderDto order) {
        if (order == null) {
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
        }
        return Uni.createFrom().item(() -> order).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(o -> orderService.storeOrder(order))
                .onItem().transform(orderDto -> Response.ok(new JsonObject().put("order", Long.toString(orderDto.getId()))).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching customer", throwable);
                    return Response.serverError().build();
                });
    }

    @POST
    @Path("/update")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> updateOrder(OrderDto order) {
        if (order == null) {
            return Uni.createFrom().item(Response.status(Response.Status.BAD_REQUEST).build());
        }
        return Uni.createFrom().item(() -> order).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(o -> orderService.updateOrder(o))
                .onItem().transform(orderDto -> {
                    if (orderDto == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    }
                    return Response.ok().build();
                })
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while updating order", throwable);
                    return Response.serverError().build();
                });
    }

    @GET
    @Path("/{customerId}/{orderId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getOrderByCustomerIdAndOrderId(@PathParam("customerId") String customerId, @PathParam("orderId") String orderId) {
        return Uni.createFrom().item(() -> null).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(n -> orderService.getOrderByCustomerIdAndOrderId(customerId, orderId))
                .onItem().transform(orderDto -> {
                    if (orderDto == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    } else {
                        return Response.ok(orderDto).build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching order by customerId and orderId", throwable);
                    return Response.serverError().build();
                });
    }

}
