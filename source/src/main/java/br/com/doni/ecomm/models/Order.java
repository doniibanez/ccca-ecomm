package br.com.doni.ecomm.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class Order {
    private Long id;
    private Calendar dateOrder;
    private List<OrderedProduct> orderedProductList;
    private String cpf;
    private DiscountCoupon discountCoupon;

    public void addDiscountCoupon(DiscountCoupon discountCoupon) {
        if (discountCoupon == null)
            return;
        this.discountCoupon = discountCoupon;
    }

    public Double calculatePrice() {
        return discountCoupon == null? calculatePriceWithoutDiscount() : calculatePriceWithDiscount();
    }

    private Double calculatePriceWithDiscount() {
        return discountCoupon.getDiscountPercentage() > 0.0 ?
                this.calculatePriceWithoutDiscount() * discountCoupon.getDiscountPercentage() / 100 : this.calculatePriceWithoutDiscount();
    }

    private Double calculatePriceWithoutDiscount() {
        Double price = 0.0;
        for (OrderedProduct orderedProduct : orderedProductList) {
            price += orderedProduct.getProduct().getPrice() * orderedProduct.getQuantity();
        }
        return price;
    }
}
