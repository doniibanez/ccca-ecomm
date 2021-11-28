package br.com.doni.ecomm.models;

import lombok.*;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class OrderedProduct {
    private Product product;
    private Integer quantity;
}
