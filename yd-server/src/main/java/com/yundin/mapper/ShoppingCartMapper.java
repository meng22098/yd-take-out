package com.yundin.mapper;

import com.yundin.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ShoppingCartMapper {

    List<ShoppingCart> list(ShoppingCart shoppingCart);
    @Update("update shopping_cart set number = #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    void insert(ShoppingCart shoppingCart);
    @Delete("delete from shopping_cart where user_id=#{id}")
    void clean(long id);

    void sub(ShoppingCart shoppingCart);
    @Update("Update shopping_cart set number = #{number} where id = #{id}")
    void Update(Long id);
}
