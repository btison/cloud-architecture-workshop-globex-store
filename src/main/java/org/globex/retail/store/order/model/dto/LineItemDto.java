package org.globex.retail.store.order.model.dto;

import java.math.BigDecimal;

public class LineItemDto {

    private String product;

    private int quantity;

    private BigDecimal price;

    public String getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final LineItemDto lineItemDto;

        public Builder() {
            this.lineItemDto = new LineItemDto();
        }

        public Builder withProduct(String product) {
            lineItemDto.product = product;
            return this;
        }

        public Builder withQuantity(Integer quantity) {
            lineItemDto.quantity = quantity;
            return this;
        }

        public Builder withPrice(BigDecimal price) {
            lineItemDto.price = price;
            return this;
        }

        public LineItemDto build() {
            return this.lineItemDto;
        }
    }
}
