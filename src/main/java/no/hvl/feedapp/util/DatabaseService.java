package no.hvl.feedapp.util;

import javax.persistence.*;

public class DatabaseService {

    public static final String PERSISTENCE_UNIT_NAME = "feedapp";
    //@Autowired
    private EntityManagerFactory factory;
    @PersistenceContext
    private EntityManager em;

    public DatabaseService() {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        em = factory.createEntityManager();
    }

    public Object persist(Object obj) {
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
        finally {
            //em.close();
        }
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
        finally {
            em.close();
        }
        return obj;
    }

    public Object remove(Object obj) {
        EntityManager em = factory.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            System.out.println(tx.isActive());
            em.remove(obj);
            System.out.println(tx.isActive());
            tx.commit();
        }
        catch (Throwable e) {
            e.printStackTrace();
            if (tx.isActive()) {
                tx.rollback();
            }
        }
        finally {
            em.close();
        }
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
