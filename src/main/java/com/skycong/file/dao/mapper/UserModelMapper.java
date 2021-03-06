package com.skycong.file.dao.mapper;


import com.skycong.file.dao.model.UserModel;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserModelMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_api_user
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);


    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_api_user
     *
     * @mbg.generated
     */
    int insertSelective(UserModel record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_api_user
     *
     * @mbg.generated
     */
    UserModel selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb_api_user
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(UserModel record);

    UserModel findByUsername(String username);
}