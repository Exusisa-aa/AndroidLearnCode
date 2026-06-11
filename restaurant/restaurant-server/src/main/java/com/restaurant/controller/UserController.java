package com.restaurant.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.restaurant.common.Result;
import com.restaurant.entity.User;
import com.restaurant.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Send Verification Code (Simulated)
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public Result<String> sendMsg(@RequestBody User user, HttpSession session){
        // Get mobile phone number
        String phone = user.getPhone();

        if(phone != null){
            // Generate random 4-digit code
            // For demo purposes, we can hardcode '1234' or generate random
            // String code = ValidateCodeUtils.generateValidateCode(4).toString();
            String code = "1234";

            log.info("Code for {} is {}", phone, code);

            // Save to session (in production use Redis)
            session.setAttribute(phone, code);

            return Result.success("Verify code sent successfully (Demo: 1234)");
        }
        return Result.error("Failed to send verify code");
    }

    /**
     * Mobile User Login
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        // Get phone and code
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();

        // Get saved code from Session
        Object codeInSession = session.getAttribute(phone);

        // Compare codes
        if(codeInSession != null && codeInSession.equals(code)){
            // Login successful

            // Check if user exists, if not register automatically
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);

            User user = userService.getOne(queryWrapper);
            if(user == null){
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            
            // Store user ID in session
            session.setAttribute("user", user.getId());

            return Result.success(user);
        }
        return Result.error("Login failed");
    }
    
    /**
     * User Logout
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout(HttpServletRequest request){
        // Clean session
        request.getSession().removeAttribute("user");
        return Result.success("Logout successful");
    }
}
