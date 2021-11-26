package me.khun.smartrestaurant.dao.custom;

import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.entity.User;

public interface UserDao extends Dao<User, Long> {

    User findByLoginId(String loginId);

    boolean existsLoginId(String loginId);

    User findByLoginIdAndPassword(String loginId, String password);
}
