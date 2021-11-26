package me.khun.smartrestaurant.dao.custom.impl;

import me.khun.smartrestaurant.dao.custom.MealCategoryDao;
import me.khun.smartrestaurant.dao.impl.HibernateDaoImpl;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.util.HibernateUtil;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import java.util.List;

class MealCategoryDaoHibernateImpl extends HibernateDaoImpl<MealCategory, Long> implements MealCategoryDao {

    @SuppressWarnings("unchecked")
    @Override
    public List<MealCategory> findByKeyWord(String keyword) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        var query = "from MealCategory where name like concat('%', :name, '%') or short_name like concat('%', :short_name, '%')";
        TypedQuery<MealCategory> typedQuery = session.createQuery(query);

        typedQuery.setParameter("name", keyword);
        typedQuery.setParameter("short_name", keyword);
        return typedQuery.getResultList();
    }
}
