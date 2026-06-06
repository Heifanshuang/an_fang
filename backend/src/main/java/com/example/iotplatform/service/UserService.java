package com.example.iotplatform.service;

import com.example.iotplatform.config.JwtUtils;
import com.example.iotplatform.dto.ApiResponse;
import com.example.iotplatform.dto.LoginRequest;
import com.example.iotplatform.entity.User;
import com.example.iotplatform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 用户登录（同时返回AccessToken）
     */
    public ApiResponse<User> login(LoginRequest request) {
        ApiResponse<User> response = new ApiResponse<>();
        
        try {
            // 根据账号或手机号查询用户
            User user = userRepository.findByAccount(request.getAccount());
            if (user == null) {
                // 如果账号查询不到，尝试使用手机号查询
                user = userRepository.findByTelephone(request.getAccount());
            }
            
            if (user == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("用户不存在");
                return response;
            }
            
            // 验证密码
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("密码错误");
                return response;
            }
            
            // 生成JWT令牌
            String token = jwtUtils.generateToken(user.getAccount());
            user.setAccessToken(token);
            user.setAccessTokenErrCode(0);
            
            // 保存用户信息
            userRepository.save(user);
            
            response.setResultObj(user);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("登录成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("登录失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 根据用户名加载用户信息（实现UserDetailsService接口）
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByAccount(username);
        if (user == null) {
            // 如果账号查询不到，尝试使用手机号查询
            user = userRepository.findByTelephone(username);
        }
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return user;
    }

    /**
     * 查询用户APIKey
     */
    public ApiResponse<User> getApiKey() {
        ApiResponse<User> response = new ApiResponse<>();
        
        try {
            // 实际项目中应从当前登录用户获取
            User user = userRepository.findById(1L).orElse(null);
            
            if (user == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("用户不存在");
                return response;
            }
            
            response.setResultObj(user);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("获取APIKey成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("获取APIKey失败：" + e.getMessage());
        }
        
        return response;
    }

    /**
     * 更新用户APIKey
     */
    public ApiResponse<Object> updateApiKey(User user) {
        ApiResponse<Object> response = new ApiResponse<>();
        
        try {
            User existingUser = userRepository.findById(user.getUserId()).orElse(null);
            
            if (existingUser == null) {
                response.setStatus(1);
                response.setStatusCode(0);
                response.setMsg("用户不存在");
                return response;
            }
            
            // 更新APIKey
            existingUser.setApiKey(user.getApiKey());
            existingUser.setApiKeyExpire(user.getApiKeyExpire());
            existingUser.setOperUserId(user.getOperUserId());
            existingUser.setClientIp(user.getClientIp());
            
            userRepository.save(existingUser);
            
            response.setResultObj(null);
            response.setStatus(0);
            response.setStatusCode(1);
            response.setMsg("更新APIKey成功");
            
        } catch (Exception e) {
            response.setStatus(1);
            response.setStatusCode(0);
            response.setMsg("更新APIKey失败：" + e.getMessage());
        }
        
        return response;
    }

}