package com.yundin.controller.user;

import com.yundin.context.BaseContext;
import com.yundin.entity.AddressBook;
import com.yundin.result.Result;
import com.yundin.service.AddressBookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/addressBook")
@Slf4j
@Api("地址接口")
public class AddressBookController {
    @Autowired
    AddressBookService addressBookService;
    @PostMapping
    @ApiOperation("新增地址")
    public Result save(@RequestBody AddressBook addressBook)
    {
        log.info("新增地址{}",addressBook);
        addressBookService.save(addressBook);
        return Result.success();
    }
    @GetMapping("/list")
    @ApiOperation("查询当前登录用户的所有地址信息")
    public Result<List<AddressBook>> list()
    {
        log.info("查询当前登录用户的所有地址信息");
        List<AddressBook> list= addressBookService.list();
        return Result.success(list);
    }
    @GetMapping("/{id}")
    @ApiOperation("根据id查询地址")
    public Result<AddressBook> getId(@PathVariable Integer id)
    {
        log.info("根据id查询地址{}",id);
        AddressBook addressBook=addressBookService.getId(id);
        return Result.success(addressBook);
    }
    @PutMapping
    @ApiOperation("根据id修改地址")
    public Result update(@RequestBody AddressBook addressBook)
    {
        log.info("根据id修改地址{}",addressBook);
        addressBookService.update(addressBook);
        return Result.success();
    }
    @DeleteMapping
    @ApiOperation("根据id删除地址")
    public Result delete(Integer id)
    {
        log.info("根据id删除地址{}",id);
        addressBookService.delete(id);
        return Result.success();
    }
    @PutMapping("/default")
    @ApiOperation("设置默认地址")
    public Result setDefault(@RequestBody AddressBook addressBook)
    {
        log.info("设置默认地址{}",addressBook);
        addressBookService.setDefault(addressBook);
        return Result.success();
    }
    @GetMapping("/default")
    @ApiOperation("查询默认地址")
    public Result<AddressBook> getDefault()
    {
        log.info("查询默认地址");
        AddressBook addressBook =addressBookService.getDefault();
       if (addressBook!=null)
       {
           return Result.success(addressBook);
       }
        return Result.error("没有查询到默认地址");

    }
}
