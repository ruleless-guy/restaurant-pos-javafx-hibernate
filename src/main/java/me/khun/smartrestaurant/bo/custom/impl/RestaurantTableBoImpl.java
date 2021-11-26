package me.khun.smartrestaurant.bo.custom.impl;

import me.khun.smartrestaurant.bo.custom.RestaurantTableBo;
import me.khun.smartrestaurant.dao.DaoType;
import me.khun.smartrestaurant.dao.custom.RestaurantTableDao;
import me.khun.smartrestaurant.dao.custom.impl.DaoFactory;
import me.khun.smartrestaurant.dto.custom.RestaurantTableDto;
import me.khun.smartrestaurant.entity.RestaurantTable;
import me.khun.smartrestaurant.entity.validator.RestaurantTableValidator;

import java.util.ArrayList;
import java.util.List;

public class RestaurantTableBoImpl implements RestaurantTableBo {

    private final RestaurantTableDao dao;

    public RestaurantTableBoImpl() {
        dao = (RestaurantTableDao) DaoFactory.getInstance().getDao(DaoType.RESTAURANT_TABLE);
    }

    @Override
    public boolean save(RestaurantTableDto dto) {
        RestaurantTable entity = RestaurantTableValidator.validate(dto.getEntity());
        return dao.save(entity);
    }

    @Override
    public boolean update(RestaurantTableDto dto) {
        RestaurantTable entity = RestaurantTableValidator.validate(dto.getEntity());
        return dao.update(entity);
    }

    @Override
    public boolean saveOrUpdate(RestaurantTableDto dto) {
        RestaurantTable entity = RestaurantTableValidator.validate(dto.getEntity());
        return dao.saveOrUpdate(entity);
    }

    @Override
    public boolean delete(RestaurantTableDto dto) {
        RestaurantTable entity = RestaurantTableValidator.validate(dto.getEntity());
        return dao.delete(entity);
    }

    @Override
    public boolean deleteById(Long id) {
        return dao.deleteById(id);
    }

    @Override
    public RestaurantTableDto findById(Long id) {
        RestaurantTable entity = dao.findById(id);
        return null == entity ? null : new RestaurantTableDto(entity);
    }

    @Override
    public List<RestaurantTableDto> findAll() {
        List<RestaurantTable> list = dao.findAll();
        return parseDtoList(list);
    }

    @Override
    public List<RestaurantTableDto> findByKeyword(String keyword) {
        List<RestaurantTable> list = dao.findByKeyword(keyword);
        return parseDtoList(list);
    }

    private List<RestaurantTableDto> parseDtoList(List<RestaurantTable> list) {
        List<RestaurantTableDto> dtoList = new ArrayList<>(list.size());

        for (RestaurantTable t : list)
            dtoList.add(new RestaurantTableDto(t));

        return dtoList;
    }
}
