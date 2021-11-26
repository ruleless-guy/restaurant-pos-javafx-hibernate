package me.khun.smartrestaurant.dao.impl;

import me.khun.smartrestaurant.application.exception.DuplicateEntryInsertionException;
import me.khun.smartrestaurant.dao.Dao;
import me.khun.smartrestaurant.entity.MealCategory;
import me.khun.smartrestaurant.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HibernateDaoImpl <T extends Serializable, ID extends Serializable> implements Dao <T, ID> {

    protected final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public HibernateDaoImpl() {
        entityClass = (Class<T>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }
    @Override
    public boolean save(T entity) {

        boolean saved = false;
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.save(entity);

            tx.commit();

            saved = true;

        }catch (ConstraintViolationException e) {
            if (null != e.getCause())
                throwDuplicateEntryException(e.getCause().getMessage());

        }catch (PersistenceException e) {

            Throwable cause = e.getCause();

            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException && cause.getMessage().startsWith("Duplicate entry"))
                    throwDuplicateEntryException(cause.getMessage());
                cause = cause.getCause();
            }

            tx.rollback();
            saved = false;
        }

        return saved;
    }

    @Override
    public boolean update(T entity) {

        boolean updated = false;
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();

            session.update(entity);
            tx.commit();

            updated = true;

        }catch (PersistenceException e) {

            Throwable cause = e.getCause();

            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException && cause.getMessage().startsWith("Duplicate entry"))
                    throwDuplicateEntryException(cause.getMessage());
                cause = cause.getCause();
            }

            tx.rollback();
            updated = false;
        }

        return updated;
    }

    @Override
    public boolean saveOrUpdate(T entity) {

        boolean saved = false;
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.saveOrUpdate(entity);
            tx.commit();

            saved = true;

        } catch (PersistenceException e) {

            Throwable cause = e.getCause();

            while (cause != null) {
                if (cause instanceof SQLIntegrityConstraintViolationException && cause.getMessage().startsWith("Duplicate entry"))
                    throwDuplicateEntryException(cause.getMessage());
                cause = cause.getCause();
            }

            tx.rollback();
            saved = false;
        }

        return saved;
    }

    @Override
    public boolean delete(T entity) {

        boolean deleted;
        Transaction tx = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            tx = session.beginTransaction();
            session.delete(entity);
            tx.commit();

            deleted = true;

        } catch (PersistenceException e) {

            tx.rollback();
            deleted = false;

        }

        return deleted;
    }

    @Override
    public boolean deleteById(ID id) {

        T entity = findById(id);
        if (null == entity) return false;

        return delete(entity);
    }

    @Override
    public T findById(ID id) {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            return session.get(entityClass, id);

        } catch (PersistenceException e) {
            System.out.println(e.getMessage());
            return null;

        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {

            TypedQuery<T> typedQuery = session.createQuery("from " + entityClass.getSimpleName());

            return typedQuery.getResultList();

        } catch (PersistenceException e) {

            return Collections.EMPTY_LIST;

        }
    }

    private static void throwDuplicateEntryException(String causeMessage) {
        System.out.println(causeMessage);
        String entry = causeMessage;
        int startIndex = entry.indexOf('\'') + 1;
        int endIndex = entry.indexOf('\'', startIndex);

        entry = entry.substring(startIndex, endIndex);

        DuplicateEntryInsertionException exception = new DuplicateEntryInsertionException("Duplicate entry for " + entry);
        exception.setEntryValue(entry);
        throw exception;
    }
}
