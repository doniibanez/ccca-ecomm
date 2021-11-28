package br.com.doni.ecomm.services;

import br.com.doni.ecomm.models.Order;
import br.com.doni.ecomm.models.OrderedProduct;
import br.com.doni.ecomm.util.DocumentUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    public Order makerOrder(List<OrderedProduct> orderedProductList , String cpf){
        if(!DocumentUtil.validate(cpf))
            throw new IllegalArgumentException("The CPF is invalid: " + cpf);

        return Order.builder().cpf(cpf).orderedProductList(orderedProductList).build();
    }
}
