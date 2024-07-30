package com.yundin.controller.user;

import com.yundin.constant.JwtClaimsConstant;
import com.yundin.dto.UserLoginDTO;
import com.yundin.entity.User;
import com.yundin.properties.JwtProperties;
import com.yundin.result.Result;
import com.yundin.service.UserService;
import com.yundin.utils.JwtUtil;
import com.yundin.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Slf4j
@Api("用户相关接口")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private JwtProperties jwtProperties;
    @PostMapping("/login")
    @ApiOperation("登录")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO)
    {
        log.info("登录{}",userLoginDTO);
        User user= userService.login(userLoginDTO);
        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();

        claims.put(JwtClaimsConstant.USER_ID,user.getId());

        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(), jwtProperties.getUserTtl(), claims);

        UserLoginVO userLoginVO = UserLoginVO.builder().id(user.getId()).openid(user.getOpenid()).token(token).build();
        return Result.success(userLoginVO);
    }
    @PostMapping("/logout")
    @ApiOperation("员工退出")
    public Result<String> logout() {
        return Result.success();
    }
}
