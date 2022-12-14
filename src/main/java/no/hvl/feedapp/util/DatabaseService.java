package no.hvl.feedapp.util;

import javax.persistence.*;
import java.util.List;

public class DatabaseService<T> {

    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    private EntityManagerFactory factory;

    public DatabaseService() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    public List<T> getAll(Class<T> modelClass, String queryString) {
        EntityManager em = factory.createEntityManager();
        Query query = em.createQuery(queryString, modelClass);
        List<T> list = query.getResultList();
        em.close();
        return list;
    }

    public T find(Class<T> modelClass, Long id) {
        EntityManager em = factory.createEntityManager();
        T model = em.find(modelClass, id);
        em.close();
        return model;
    }

    public Object persist(Object obj) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.persist(obj);
            tx.commit();
        }
        catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        finally {em.close();}
        return obj;
    }

    public Object merge(Object obj) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            em.merge(obj);
            tx.commit();
        }
        catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        finally {em.close();}
        return obj;
    }

    public Object remove(Object obj) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            if (!em.contains(obj)) {
                obj = em.merge(obj);
            }
            em.remove(obj);
            tx.commit();
        }
        catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        finally { em.close();}
        return obj;
    }

    public static boolean isNumber(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
