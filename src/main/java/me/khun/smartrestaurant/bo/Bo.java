package me.khun.smartrestaurant.bo;

import me.khun.smartrestaurant.dto.Dto;
import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.entity.BaseEntity;

import java.io.Serializable;

public interface Bo <T extends Dto<? extends BaseEntity<ID>>, ID extends Serializable> extends Dao<T, ID> {

}
