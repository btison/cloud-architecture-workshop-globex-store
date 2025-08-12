package org.globex.retail.store.customer.rest;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.globex.retail.store.customer.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/services")
public class CustomerResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerResource.class);

    @Inject
    CustomerService customerService;

    @GET
    @Path("/customer/id/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCustomerByUserId(@PathParam("userId") String userId) {
        return Uni.createFrom().item(() -> userId).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(u -> customerService.getCustomerByCustomerId(u))
                .onItem().transform(customerDto -> {
                    if (customerDto == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    } else {
                        return Response.ok(customerDto).build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching customer", throwable);
                    return Response.serverError().build();
                });
    }

}
