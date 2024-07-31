package com.yundin.controller.user;

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
}
