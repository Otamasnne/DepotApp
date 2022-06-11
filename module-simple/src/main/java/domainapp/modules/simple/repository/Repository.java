package domainapp.modules.simple.repository;

import javax.inject.Inject;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
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
    public <T> void save(List<T> objetos) {

    }

    @Override
    public <T> Object retrieve(Class<T> clase, String id) {
        return null;
    }

    @Override
    public <T> List<T> consultar(Class<T> clase) {
        return null;
    }
}
