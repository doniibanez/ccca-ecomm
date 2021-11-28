package br.com.doni.ecomm.models;

import lombok.*;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class Product {
    private Long id;
    private String productName;
    private Double weight;
    private Double price;
}
