package com.film.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.film.backend.dto.LoginDTO;
import com.film.backend.dto.RegisterDTO;
import com.film.backend.entity.User;

public interface IUserService extends IService<User> {
    User login(LoginDTO loginDTO);
    void register(RegisterDTO registerDTO);
}
