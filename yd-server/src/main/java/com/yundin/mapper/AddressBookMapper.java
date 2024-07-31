package com.yundin.mapper;

import com.yundin.entity.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    void save(AddressBook addressBook);
    @Select("select * from address_book where user_id=#{id}")
    List<AddressBook> list(Long id);
}
