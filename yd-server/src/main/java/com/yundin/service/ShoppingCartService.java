package com.yundin.service;

import com.yundin.dto.ShoppingCartDTO;
import com.yundin.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    List<ShoppingCart> list();
}
