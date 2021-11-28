package br.com.doni.ecomm.models;

import lombok.*;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class DiscountCoupon {
    private Long id;
    private String name;
    private double discountPercentage;
}
