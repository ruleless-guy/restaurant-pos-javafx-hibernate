package me.khun.smartrestaurant.dao.custom.impl;

import me.khun.smartrestaurant.dao.custom.RestaurantTableDao;
import me.khun.smartrestaurant.dao.impl.HibernateDaoImpl;
import me.khun.smartrestaurant.entity.RestaurantTable;
import me.khun.smartrestaurant.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

public class RestaurantTableDaoHibernateImpl extends HibernateDaoImpl<RestaurantTable, Long> implements RestaurantTableDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<RestaurantTable> findByKeyword(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var query = "from RestaurantTable where name like concat('%', :name, '%')";
        TypedQuery<RestaurantTable> typedQuery = session.createQuery(query);

        typedQuery.setParameter("name", keyword);
        return typedQuery.getResultList();
    }
}
