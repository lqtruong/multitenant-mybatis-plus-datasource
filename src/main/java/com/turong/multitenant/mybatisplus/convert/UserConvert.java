package com.turong.multitenant.mybatisplus.convert;

import com.turong.multitenant.mybatisplus.controller.UserResponse;
import com.turong.multitenant.mybatisplus.controller.UserSaveRequest;
import com.turong.multitenant.mybatisplus.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserConvert {

    User toUser(final UserSaveRequest userSaveRequest);

    UserResponse toResponse(final User user);

}
