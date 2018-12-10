package com.skycong.file.service;

import com.skycong.file.dao.mapper.InterfaceModelMapper;
import com.skycong.file.dao.mapper.UserModelMapper;
import com.skycong.file.dao.model.InterfaceModel;
import com.skycong.file.dao.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

/**
 * @author RMC 2018/11/23 13:08
 */
@Service
public class InterfaceService {

    @Autowired
    private InterfaceModelMapper interfaceMapper;


    public List<InterfaceModel> findAll() {
        return interfaceMapper.findAll();
    }

    public void save(InterfaceModel save) {

        interfaceMapper.insertSelective(save);
    }

    public InterfaceModel findById(Integer id) {
        return interfaceMapper.selectByPrimaryKey(id);
    }

    public List<InterfaceModel> search(String keyword) {
        return interfaceMapper.findByKeyword(keyword);
    }

    public void delete(Integer id) {
        interfaceMapper.deleteByPrimaryKey(id);
    }

    public void update(InterfaceModel update) {
        interfaceMapper.updateByPrimaryKeySelective(update);
    }
}
