package com.skycong.file.service;

import com.skycong.file.dao.mapper.UserModelMapper;
import com.skycong.file.dao.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author RMC 2018/11/23 13:08
 */
@Service
public class UserService {

    @Autowired
    private UserModelMapper userMapper;

    public UserModel findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    public void save(UserModel save) {
        userMapper.insertSelective(save);
    }


    public UserModel getLoginUser() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object user = requestAttributes.getRequest().getSession().getAttribute("USER");
        return (UserModel) user;
    }


}
