package com.yundin.mapper;

import com.yundin.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Mapper
public interface UserMapper {
    @Select("select * from user where openId=#{openId}")
    User login(String openId);
    void insert(User user);
    Integer getUserCount(LocalDateTime beginTime, LocalDateTime endTime);
}
