package com.film.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.film.backend.dto.LoginDTO;
import com.film.backend.dto.RegisterDTO;
import com.film.backend.entity.User;
import com.film.backend.mapper.UserMapper;
import com.film.backend.service.IUserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Override
    public User login(LoginDTO loginDTO) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = getOne(wrapper);
        
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        
        return user;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        // Check if username exists
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerDTO.getUsername());
        if (count(queryWrapper) > 0) {
            throw new RuntimeException("Username already exists");
        }

        // Check if phone exists
        queryWrapper.clear();
        queryWrapper.eq(User::getPhone, registerDTO.getPhone());
        if (count(queryWrapper) > 0) {
            throw new RuntimeException("Phone number already registered");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(registerDTO.getPassword());
        user.setPhone(registerDTO.getPhone());
        user.setPoints(0);
        // Set a default avatar
        user.setAvatar("https://api.dicebear.com/7.x/avataaars/svg?seed=" + registerDTO.getUsername());
        
        save(user);
    }
}
