package com.yundin.mapper;

import com.yundin.entity.AddressBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AddressBookMapper {
    void save(AddressBook addressBook);
    @Select("select * from address_book where user_id=#{id}")
    List<AddressBook> list(Long id);
    @Select("select * from address_book where id=#{id}")
    AddressBook getId(Integer id);

    void update(AddressBook addressBook);
    @Delete("delete from address_book where id=#{id}")
    void delete(Integer id);
    @Update("update address_book set is_default=0 where user_id=#{id}")
    void deleteAll(long userId);
    @Select("select * from address_book where user_id=#{userId} and is_default=#{isDefault}")
    AddressBook getDefault(AddressBook addressBook);
}
