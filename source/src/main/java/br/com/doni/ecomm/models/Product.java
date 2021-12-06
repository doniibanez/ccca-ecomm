package br.com.doni.ecomm.models;

import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class Product {
    private Long id;
    private String productName;
    private Double price;
    private Dimension dimension;
    private Double weight;

    public Double getVolume() {
        return this.dimension.calculateVolume();
    }

    public Double getDensity() {
        return weight / getVolume();
    }

    public BigDecimal getShippingFee() {
        return new BigDecimal(1000 * this.getVolume() * (this.getDensity() / 100));
    }
}
