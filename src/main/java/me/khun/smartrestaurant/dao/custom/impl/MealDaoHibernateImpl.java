package me.khun.smartrestaurant.dao.custom.impl;

import me.khun.smartrestaurant.dao.custom.MealDao;
import me.khun.smartrestaurant.dao.impl.HibernateDaoImpl;
import me.khun.smartrestaurant.entity.Meal;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

class MealDaoHibernateImpl extends HibernateDaoImpl<Meal, Long> implements MealDao {

    @Override
    public List<Meal> findByKeyWord(String keyword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        var query = "from Meal where name like concat('%', :name, '%') or short_name like concat('%', :short_name, '%')";
        TypedQuery<Meal> typedQuery = session.createQuery(query);

        typedQuery.setParameter("name", keyword);
        typedQuery.setParameter("short_name", keyword);
        return typedQuery.getResultList();
    }
}
