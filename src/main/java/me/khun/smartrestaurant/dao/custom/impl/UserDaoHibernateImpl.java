package me.khun.smartrestaurant.dao.custom.impl;

import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.dao.custom.UserDao;
import me.khun.smartrestaurant.dao.impl.HibernateDaoImpl;
import me.khun.smartrestaurant.entity.Gender;
import me.khun.smartrestaurant.entity.User;
import me.khun.smartrestaurant.entity.UserRole;
import me.khun.smartrestaurant.util.DateTimeUtil;
import me.khun.smartrestaurant.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import javax.persistence.Query;
import java.time.LocalDate;

class UserDaoHibernateImpl extends HibernateDaoImpl <User, Long> implements UserDao {


    @Override
    public boolean delete(User user) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Transaction tx = session.beginTransaction();

            String hqlForMealCategory = "update MealCategory set added_user_id=null where added_user_id=:id";
            String hqlForMeal = "update Meal set added_user_id=null where added_user_id=:id";

            Query queryForMealCategory = session.createQuery(hqlForMealCategory);
            queryForMealCategory.setParameter("id", user.getId());

            Query queryForMeal = session.createQuery(hqlForMeal);
            queryForMeal.setParameter("id", user.getId());

            queryForMealCategory.executeUpdate();
            queryForMeal.executeUpdate();
            tx.commit();

            return super.delete(user);

        }catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public User findByLoginId(String loginId) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query query = session.createQuery("from " + entityClass.getSimpleName() + " where login_id=:login_id");
            query.setParameter("login_id", loginId);

            return  (User) query.getSingleResult();
        } catch (PersistenceException e) {
            return null;
        }
    }

    @Override
    public User findByLoginIdAndPassword(String loginId, String password) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            Query query = session.createQuery("from " + entityClass.getSimpleName() + " where login_id=:login_id and password=:password");
            query.setParameter("login_id", loginId);
            query.setParameter("password", password);

            return (User) query.getSingleResult();
        } catch (PersistenceException e) {
            System.out.println(getClass().getSimpleName() + " : findByLoginIdAndPassword : not found");
            return null;
        }
    }

    @Override
    public boolean existsLoginId(String loginId) {
        return findByLoginId(loginId) != null;
    }

    public static void main(String[] args) {
        UserDaoHibernateImpl dao = new UserDaoHibernateImpl();
        User user = new User();

        user.setUserName(new User.UserName("Khun", "Pyae Phyo Aung"));
        user.setAddress("Zayatkyi");
        user.setGender(Gender.MALE);
        user.setRole(UserRole.CASHIER);
        user.setLoginId("aaa");
        user.setPassword("aaa");
        user.setPhone("09254575210");
        user.setProfileImagePath("profile/image/path");
        user.setStatus(User.UserStatus.ACTIVE);
        LocalDate localDate = LocalDate.of(2021, 11, 5);
        user.setDateOfBirth(DateTimeUtil.localDateToDate(localDate));

        try {
            dao.save(user);
        } catch (DuplicateEntryInsertionException e) {
            System.out.println(e.getMessage());
        }

//        dao.deleteById(1L);
    }


}
