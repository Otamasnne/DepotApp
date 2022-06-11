package domainapp.modules.simple.repository;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;
import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Repository
public class Repository implements IRepository {

    @Inject
    private PersistenceManagerFactory pmf;


    @Override
    public void save(Object object) {

        PersistenceManager pm = this.pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try{
            tx.begin();
            pm.makePersistent(object);
            tx.commit();
        }
        finally {
            if(tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public <T> void save(List<T> objects) {
        PersistenceManager pm = this.pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();
        try{
            tx.begin();
            pm.makePersistentAll(objects);
            tx.commit();
        }finally {
            if(tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
    }

    @Override
    public <T> Object retrieve(Class<T> clase, String id) {
        Object object = null;

        PersistenceManager pm = this.pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try {
            tx.begin();
            object = pm.getObjectById(clase,id);
            tx.commit();
        }finally {
            if(tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }
        return object;
    }

    @Override
    public <T> List<T> retrieve(Class<T> clase) {
        List<T> list = new ArrayList<T>();

        PersistenceManager pm = this.pmf.getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        try{
            tx.begin();
            Query<T> query = pm.newQuery(clase);
            list = query.executeList();
            tx.commit();
        }finally {
            if(tx.isActive()){
                tx.rollback();
            }
            pm.close();
        }

        return list;
    }
}
