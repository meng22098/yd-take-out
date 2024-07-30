package com.yundin.mapper;

import com.yundin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{openId}")
    User login(String openId);
    void insert(User user);
}
