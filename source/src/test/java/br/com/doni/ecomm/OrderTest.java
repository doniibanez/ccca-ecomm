package br.com.doni.ecomm;

import br.com.doni.ecomm.models.DiscountCoupon;
import br.com.doni.ecomm.models.Order;
import br.com.doni.ecomm.models.OrderedProduct;
import br.com.doni.ecomm.models.Product;
import br.com.doni.ecomm.services.OrderService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = EcommerceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
class OrderTest {
    private final String CPF_INVALID = "123.123.123-222";
    private final String CPF_VALID = "764.286.220-25";
    private final Double PRODUCT_PRICE = 10.0;

    @Autowired
    private OrderService orderService;

    @Test
    void placeOrderWithInvalidCpf() {
        List orderedProducts = createOrderedProducts(1);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> orderService.makerOrder(orderedProducts, CPF_INVALID));
    }

    @Test
    void placeOrderWithTreeItems() {
        List orderedProducts = createOrderedProducts(3);
        Order order = orderService.makerOrder(orderedProducts, CPF_VALID);
        Assertions.assertTrue(order.getOrderedProductList().size() == 3);
        Assertions.assertEquals(order.calculatePrice(), PRODUCT_PRICE * order.getOrderedProductList().size(), 0);
    }

    @Test
    void placeOrderWithDiscount() {
        List orderedProducts = createOrderedProducts(1);
        DiscountCoupon discountCoupon = new DiscountCoupon(1L, "50% off", 50);
        Order order = orderService.makerOrder(orderedProducts, CPF_VALID);
        order.addDiscountCoupon(discountCoupon);
        Assertions.assertTrue(order.getOrderedProductList().size() == 1);
        Assertions.assertEquals(order.calculatePrice(), (PRODUCT_PRICE * order.getOrderedProductList().size()) * discountCoupon.getDiscountPercentage() / 100, 0);
    }

    public List<OrderedProduct> createOrderedProducts(Integer numberItemsInOrder) {
        List orderedProducts = new ArrayList();
        for (long countItems = 0; countItems < numberItemsInOrder; countItems++) {
            orderedProducts.add(new OrderedProduct(buildProduct(countItems, PRODUCT_PRICE), 1));
        }
        return orderedProducts;
    }

    Product buildProduct(Long id, Double price) {
        return new Product(id, "Product-" + id, 10.0, price);
    }
}
