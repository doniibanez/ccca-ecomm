package br.com.doni.ecomm.models;

import lombok.*;

import java.util.Calendar;

@Getter @Setter @Builder @EqualsAndHashCode @AllArgsConstructor
public class DiscountCoupon {
    private Long id;
    private String name;
    private Double discountPercentage;
    private Calendar expirationDate;

    public boolean isExpiredCoupon() {
        return Calendar.getInstance().after(this.expirationDate);
    }
}
