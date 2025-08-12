package org.globex.retail.store.catalog.rest;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.globex.retail.store.catalog.service.CatalogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

@Path("/services/catalog")
public class CatalogResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogResource.class);

    @Inject
    CatalogService catalogService;

    @GET
    @Path("/product/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> read(@PathParam("id") String id, @QueryParam("inventory") Boolean inventory) {
        final boolean inv = inventory == null || inventory;
        return Uni.createFrom().item(() -> id).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(productId -> catalogService.read(productId, inv))
                .onItem().transform(productDto -> {
                    if (productDto == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    } else {
                        return Response.ok(productDto).build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching product by id", throwable);
                    return Response.serverError().build();
                });
    }

    @GET
    @Path("/product/list/{ids}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readList(@PathParam("ids") String ids, @QueryParam("inventory") Boolean inventory) {
        final boolean inv = inventory == null || inventory;
        return Uni.createFrom().item(() -> Arrays.stream(ids.split(",")).collect(Collectors.toList())).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(idList -> catalogService.readByIds(idList, inv))
                .onItem().transform(productDtoList -> Response.ok(productDtoList).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching products by id list", throwable);
                    return Response.serverError().build();
                });
    }

    @GET
    @Path("/product")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readAll(@QueryParam("page") Integer page, @QueryParam("limit") Integer limit, @QueryParam("inventory") Boolean inventory) {
        final boolean inv = inventory == null || inventory;
        final int pageIndex = page == 0? 0 : page-1;
        return Uni.createFrom().item(() -> null).emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(o -> {
                    if (limit == null) {
                        return catalogService.readAll(inv);
                    } else {
                        return catalogService.readAll(pageIndex, limit, inv);
                    }
                })
                .onItem().transform(pagedProductList -> Response.ok(pagedProductList).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching paged product list", throwable);
                    return Response.serverError().build();
                });
   }

    @GET
    @Path("/product/category/{categories}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readByCategories(@PathParam("categories") String categories, @QueryParam("inventory") Boolean inventory) {
        final boolean inv = inventory == null || inventory;
        return Uni.createFrom().item(() -> Arrays.stream(categories.split(",")).collect(Collectors.toList()))
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(categoryList -> catalogService.readByCategories(categoryList, inv))
                .onItem().transform(productDtos -> Response.ok(productDtos).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching products by category list", throwable);
                    return Response.serverError().build();
                });
    }

    @GET
    @Path("/product/tag/{tags}")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> readByTags(@PathParam("tags") String tags, @QueryParam("inventory") Boolean inventory) {
        final boolean inv = inventory == null || inventory;
        return Uni.createFrom().item(() -> Arrays.stream(tags.split(",")).collect(Collectors.toList()))
                .emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(tagList -> catalogService.readByTags(tagList, inv))
                .onItem().transform(productDtos -> Response.ok(productDtos).build())
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching products by tag list", throwable);
                    return Response.serverError().build();
                });
    }

    @GET
    @Path("/category/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> getCategoryList() {
        
        return Uni.createFrom().voidItem().emitOn(Infrastructure.getDefaultWorkerPool())
                .onItem().transform(catalogList -> catalogService.getCategoryList())    
                .onItem().transform(catalogList -> {
                    if (catalogList == null) {
                        return Response.status(Response.Status.NOT_FOUND).build();
                    } else {
                        return Response.ok(catalogList).build();
                    }
                })
                .onFailure().recoverWithItem(throwable -> {
                    LOGGER.error("Exception while fetching category list", throwable);
                    return Response.serverError().build();
                });          
            }     
        
}
