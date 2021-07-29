package com.turong.multitenant.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.turong.multitenant.mybatisplus.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

public interface UserMapper extends BaseMapper<User> {

    @Select("select * from users where id = #{id}")
    @Options(useCache = false)
    User findUser(final String id);

    @Insert("insert into users(username,email,tenant) values(#{username},#{email},#{tenantId})")
    int insert(final User userToCreate);

    @Select("select * from users where email = #{email}")
    Optional<User> findUserByEmail(final String email);

    @Delete("delete from users where email = #{email}")
    int deleteByEmail(final String email);

    @Delete("delete from users where id = #{id}")
    int deleteById(String id);

}
