package org.globex.retail.store.catalog.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.globex.retail.store.catalog.model.dto.PagedProductList;
import org.globex.retail.store.catalog.model.dto.ProductDto;
import org.globex.retail.store.catalog.model.dto.ProductMapper;
import org.globex.retail.store.catalog.model.entity.Product;
import org.globex.retail.store.inventory.model.dto.InventoryDto;
import org.globex.retail.store.inventory.service.InventoryService;
import org.globex.retail.store.catalog.model.entity.Category;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class CatalogService {

    @Inject
    InventoryService inventoryService;

    public ProductDto read(String id, boolean inventory) {
        Product product = Product.findById(id);
        if (product == null) {
            return null;
        }
        if (inventory) {
            int quantity;
            List<InventoryDto> inventoryList = inventoryService.findByItemId(product.itemId);
            if (inventoryList.isEmpty()) {
                quantity = 0;
            } else {
                quantity = inventoryList.get(0).getQuantity();
            }
            return ProductMapper.toDto(product, quantity);
        }
        return ProductMapper.toDto(product);
    }

    public List<ProductDto> readByIds(List<String> ids, boolean inventory) {
        List<Product> productList = Product.findByItemIdList(ids);
        if (inventory) {
            return toDtoWithInventory(productList);
        }
        return toDto(productList);
    }

    public PagedProductList readAll(int page, int limit, boolean inventory) {
        List<Product> productList = Product.findProducts(page, limit);
        List<ProductDto> productDtoList;
        if (inventory) {
            productDtoList = toDtoWithInventory(productList);
        } else {
            productDtoList = toDto(productList);
        }
        int count = Math.toIntExact(Product.count());
        return PagedProductList.builder()
                .withProducts(productDtoList)
                .withTotalElements(count)
                .withTotalPages((int) Math.ceil((double) count / (double) limit))
                .withNumberOfElements(productDtoList.size())
                .withSize(limit)
                .withNumber(page)
                .build();
    }

    public PagedProductList readAll(boolean inventory) {
        return readAll(0, Math.toIntExact(Product.count()), inventory);
    }

    public List<ProductDto> readByCategories(List<String> categories, boolean inventory) {
        List<Product> productList = Product.findByCategoryList(categories);
        if (inventory) {
            return toDtoWithInventory(productList);
        }
        return toDto(productList);
    }

    public List<ProductDto> readByTags(List<String> tags, boolean inventory) {
        List<Product> productList = Product.findByTagList(tags);
        if (inventory) {
            return toDtoWithInventory(productList);
        }
        return toDto(productList);
    }

    private List<ProductDto> toDtoWithInventory(List<Product> productList) {
        return productList.stream().map(product -> {
            int quantity;
            List<InventoryDto> inventoryList = inventoryService.findByItemId(product.itemId);
            if (inventoryList.isEmpty()) {
                quantity = 0;
            } else {
                quantity = inventoryList.get(0).getQuantity();
            }
            return ProductMapper.toDto(product, quantity);
        }).collect(Collectors.toList());
    }

    private List<ProductDto> toDto(List<Product> productList) {
        return productList.stream().map(ProductMapper::toDto).collect(Collectors.toList());
    }

    
    public List<Category> getCategoryList() {
        return Category.listAll();
    }

}
