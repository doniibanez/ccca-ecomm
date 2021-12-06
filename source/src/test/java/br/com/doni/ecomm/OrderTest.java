package br.com.doni.ecomm;

import br.com.doni.ecomm.models.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = EcommerceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class OrderTest {
    private final String CPF_INVALID = "123.123.123-222";
    private final String CPF_VALID = "764.286.220-25";
    private Order order;

    @BeforeEach
    public void setup() {
        order = new Order(CPF_VALID);
        order.addItem(new Product(1L, "Processador", 1200.00, new Dimension(10.0, 10.0, 1.00), 3.0), 1);
        order.addItem(new Product(2L, "Pente de Memoria RAM 8GB", 350.00, new Dimension(1.0, 1.5, 0.3), 2.0), 2);
        order.addItem(new Product(3L, "HD SATA 500GB", 200.00, new Dimension(2.0, 2.0, 0.5), 1.0), 1);
    }

    @Test
    void placeOrderWithInvalidCpf() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Order(CPF_INVALID));
    }

    @Test
    void placeOrderHasCPF() {
        assertThat(order.getCpf()).isNotNull();
    }

    @Test
    void placeOrderWithTreeItems() {
        assertThat(order.getOrderedProductList().size() == 3).isTrue();
        assertThat(order.calculatePrice()).isEqualTo(2100.00);
    }

    @Test
    void placeOrderWithDiscount() {
        DiscountCoupon discountCoupon = new DiscountCoupon(1L, "50% off", 50.00, Calendar.getInstance());
        order.applyCoupon(discountCoupon);
        assertThat(order.getDiscountCoupon()).isNotNull();
        assertThat(order.calculatePrice()).isEqualTo(1050.00);
    }

    @Test
    void applyExpiredDiscountCoupon() {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.set(Calendar.MONTH, -3);
        var coupon = new DiscountCoupon(1l, "25% OFF", 25.00, expirationDate);
        assertThat(order.applyCoupon(coupon)).isFalse();
        assertThat(order.calculatePrice()).isEqualTo(2100.00);
    }

    @Test
    void calculateShippingFee() {
        order.calculateShippingFee();
        assertThat(order.getShippingFee()).isEqualTo(80.0);
    }

    @Test
    void calculateMinimumShippingFee() {
        var order = new Order(CPF_VALID);
        order.addItem(new Product(1L, "Escova", 1200.00, new Dimension(5.0, 1.0, 0.5), 1.0), 1);
        order.calculateShippingFee();
        assertThat(order.getShippingFee()).isEqualTo(Order.MINIMUM_SHIPPING_FEE);
    }
}
