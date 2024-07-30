package com.yundin.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yundin.constant.MessageConstant;
import com.yundin.dto.UserLoginDTO;
import com.yundin.entity.User;
import com.yundin.exception.LoginFailedException;
import com.yundin.mapper.UserMapper;
import com.yundin.properties.WeChatProperties;
import com.yundin.service.UserService;
import com.yundin.utils.HttpClientUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    //微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    //微信的属性
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;
    /**
     * 微信登录
     * @param userLoginDTO
     * @return
     */
    @Override
    public User login(UserLoginDTO userLoginDTO) {
        String openId=getOpenId(userLoginDTO.getCode());
        if (openId==null)
        {
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }
        User user=userMapper.login(openId);
        if (user==null)
        {
            user = User.builder().openid(openId).createTime(LocalDateTime.now()).build();
            userMapper.insert(user);//后绪步骤实现
        }
        //返回这个用户对象
        return user;
    }
    /**
     * 调用微信接口服务，获取微信用户的openid
     * @param code
     * @return
     */
    private String getOpenId(String code){
        //调用微信接口服务，获得当前微信用户的openid
        Map<String, String> map = new HashMap<>();
        map.put("appid",weChatProperties.getAppid());
        map.put("secret",weChatProperties.getSecret());
        map.put("js_code",code);
        map.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, map);//发起请求
        JSONObject jsonObject = JSON.parseObject(json);//解析json
        String openid = jsonObject.getString("openid");//获取openid
        return openid;
    }
}
