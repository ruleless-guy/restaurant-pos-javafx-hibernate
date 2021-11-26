package me.khun.smartrestaurant.bo.custom.impl;

import me.khun.smartrestaurant.dao.custom.UserDao;
import me.khun.smartrestaurant.dto.custom.UserDto;
import me.khun.smartrestaurant.bo.custom.UserBo;
import me.khun.smartrestaurant.dao.custom.impl.DaoFactory;
import me.khun.smartrestaurant.dao.DaoType;
import me.khun.smartrestaurant.entity.User;

import java.util.ArrayList;
import java.util.List;

class UserBoImpl implements UserBo {

    private final UserDao userDao;

    public UserBoImpl() {
        userDao = (UserDao) DaoFactory.getInstance().getDao(DaoType.USER);
    }

    @Override
    public boolean save(UserDto dto) {
        return userDao.save(dto.getEntity());
    }

    @Override
    public boolean update(UserDto dto) {
        return userDao.update(dto.getEntity());
    }

    @Override
    public boolean saveOrUpdate(UserDto dto) {
        return userDao.saveOrUpdate(dto.getEntity());
    }

    @Override
    public boolean delete(UserDto dto) {
        return userDao.delete(dto.getEntity());
    }

    @Override
    public boolean deleteById(Long id) {
        return userDao.deleteById(id);
    }

    @Override
    public UserDto findById(Long id) {
        User user = userDao.findById(id);
        return null == user  ? null : new UserDto(user);
    }

    @Override
    public List<UserDto> findAll() {

        List<User> userList = userDao.findAll();
        List<UserDto> userDtoList = new ArrayList<>(userList.size());

        for (User user : userList)
            userDtoList.add(new UserDto(user));

        return userDtoList;
    }

    @Override
    public UserDto findByLoginId(String loginId) {
        User user = userDao.findByLoginId(loginId);
        return null == user ? null : new UserDto(user);
    }

    @Override
    public UserDto findByLoginIdAndPassword(String loginId, String password) {
        User user = userDao.findByLoginIdAndPassword(loginId, password);
        return null == user ? null : new UserDto(user);
    }

    @Override
    public boolean loginIdExists(String loginId) {
        return findByLoginId(loginId) != null;
    }
}
