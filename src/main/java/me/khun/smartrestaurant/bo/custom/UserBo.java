package me.khun.smartrestaurant.bo.custom;

import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.bo.Bo;

public interface UserBo extends Bo <UserDto, Long> {

    UserDto findByLoginId(String loginId);

    UserDto findByLoginIdAndPassword(String loginId, String password);

    boolean loginIdExists(String loginId);
}
