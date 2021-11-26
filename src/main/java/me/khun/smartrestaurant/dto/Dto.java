package me.khun.smartrestaurant.dto;

import java.io.Serializable;

public interface Dto <T extends Serializable> extends Serializable{

    T getEntity();
}
