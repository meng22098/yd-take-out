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
}
