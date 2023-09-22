package com.quan.studyspringsecurity.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.quan.studyspringsecurity.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.quan.studyspringsecurity.entity.Users;

import java.util.List;


@Service("userDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // 调用数据库方法查询用户信息：更具用户名
        QueryWrapper<Users> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        Users thisUser = userMapper.selectOne(queryWrapper);
        if (thisUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 密码加密
        String password = passwordEncoder.encode(thisUser.getPassword());
        List<GrantedAuthority> auths = AuthorityUtils
                .commaSeparatedStringToAuthorityList("role");

        return new User(thisUser.getUsername(), password, auths);
    }
}
