package com.yundin.service.impl;

import com.yundin.context.BaseContext;
import com.yundin.entity.AddressBook;
import com.yundin.mapper.AddressBookMapper;
import com.yundin.service.AddressBookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AddressBookServiceImpl implements AddressBookService {
    @Autowired
    AddressBookMapper addressBookMapper;
    /**
     * 新增地址
     * @param addressBook
     */
    @Override
    public void save(AddressBook addressBook) {
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(0);
        addressBookMapper.save(addressBook);
    }

    /**
     * 查询当前登录用户的所有地址信息
     * @return
     */
    @Override
    public List<AddressBook> list() {
        return addressBookMapper.list(BaseContext.getCurrentId());
    }

    /**
     * 根据id查询地址
     * @param id
     * @return
     */
    @Override
    public AddressBook getId(Integer id) {
        return addressBookMapper.getId(id);
    }

    /**
     * 根据id修改地址
     * @param addressBook
     */
    @Override
    public void update(AddressBook addressBook) {
        addressBookMapper.update(addressBook);
    }

    /**
     * 根据id删除地址
     * @param id
     */
    @Override
    public void delete(Integer id) {
        addressBookMapper.delete(id);
    }

    /**
     * 设置默认地址
     * @param addressBook
     */
    @Override
    public void setDefault(AddressBook addressBook) {
        //全部清理
       long userId = BaseContext.getCurrentId();
       addressBookMapper.deleteAll(userId);
       addressBook.setIsDefault(1);
       addressBookMapper.update(addressBook);
    }

    /**
     * 查询默认地址
     * @return
     */
    @Override
    public AddressBook getDefault() {
        AddressBook addressBook=new AddressBook();
        addressBook.setUserId(BaseContext.getCurrentId());
        addressBook.setIsDefault(1);
        return addressBookMapper.getDefault(addressBook);
    }
}
