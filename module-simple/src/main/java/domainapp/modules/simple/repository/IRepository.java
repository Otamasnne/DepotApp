package domainapp.modules.simple.repository;

import java.util.List;

public interface IRepository {

    void save(Object object);

    <T> void save(List<T> objetos);

    <T> Object retrieve(Class<T> clase, String id);

    <T> List<T> consultar(Class<T> clase);
}
