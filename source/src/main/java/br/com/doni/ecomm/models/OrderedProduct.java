package br.com.doni.ecomm.models;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class OrderedProduct {
    private Product product;
    private Integer quantity;
    private Double total;

    public OrderedProduct(Product product, Integer quantity) {
        this.product = product;
        this.quantity = quantity;
        this.total = product.getPrice() * quantity;
    }

    public BigDecimal calculateTotalShippingFee() {
        return product.getShippingFee().multiply(new BigDecimal(quantity));
    }
}
