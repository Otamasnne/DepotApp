package domainapp.modules.simple.dom.articulo;

import domainapp.modules.simple.dom.pedidos.Pedido;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Transaction;
import java.util.List;

@Repository
public interface ArticuloRepository {


    List<Articulo> findByArt(Articulo articulo);


//    @Inject
//    private PersistenceManagerFactory pmf;
//    @Override
//    public List<Articulo> findByPedido(Pedido pedido) {
//        Articulo articulo = null;
//
//        PersistenceManager pm = this.pmf.getPersistenceManager();
//        Transaction tx = pm.currentTransaction();
//
//        try {
//
//            tx.begin();
//
//            JDOQLTypedQuery<Articulo> tq = pm.newJDOQLTypedQuery(Articulo.class);
//
//
//
////            QArticulo cand = QArticulo.candidate();
////            QPedido var = QPedido.variable("var");
//
////            articulo = tq.filter(cand.numero.eq(numero)).executeUnique();
//
//            tx.commit();
//        }
//        finally {
//            if(tx.isActive()) {
//                tx.rollback();
//            }
//            pm.close();
//        }
//
//        return (List<Articulo>) articulo;
//    }


}

