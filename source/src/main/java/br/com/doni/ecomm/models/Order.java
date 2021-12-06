package br.com.doni.ecomm.models;

import lombok.Getter;
import lombok.Setter;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter @Setter
public class Order {
    public final static Double MINIMUM_SHIPPING_FEE = 10.0;

    private Long id;
    private List<OrderedProduct> orderedProductList = new ArrayList<>();
    private CPF cpf;
    private DiscountCoupon discountCoupon;
    private Double shippingFee = 0.0;

    public Order(String cpf) {
        this.cpf = new CPF(cpf);
    }

    public Double calculatePrice() {
        return Objects.isNull(discountCoupon) ? calculateTotalPrice() : calculateTotalPrice() * discountCoupon.getDiscountPercentage() / 100;
    }

    public void addItem(Product product, int quantity) {
        orderedProductList.add(new OrderedProduct(product, quantity));
    }

    private Double calculateTotalPrice() {
        Double price = 0.0;
        for (OrderedProduct orderedProduct : orderedProductList) {
            price += orderedProduct.getTotal();
        }
        return price;
    }

    public boolean applyCoupon(DiscountCoupon coupon) {
        if (Objects.isNull(coupon) || coupon.isExpiredCoupon()) return false;
        this.discountCoupon = coupon;
        return true;
    }

    public void calculateShippingFee() {
        for (OrderedProduct orderedProduct : orderedProductList) {
            var total = orderedProduct.calculateTotalShippingFee();
            total.setScale(2, RoundingMode.HALF_UP);
            this.shippingFee += total.doubleValue();
        }
        shippingFee = shippingFee > MINIMUM_SHIPPING_FEE? shippingFee : MINIMUM_SHIPPING_FEE;
    }
}
