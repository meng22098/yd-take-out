package com.yundin.service;

import com.yundin.dto.UserLoginDTO;
import com.yundin.entity.User;

public interface UserService {
    User login(UserLoginDTO userLoginDTO);
}
