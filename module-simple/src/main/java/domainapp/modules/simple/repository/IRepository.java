package domainapp.modules.simple.repository;

import java.util.List;

public interface IRepository {

    void save(Object object);

    <T> void save(List<T> objects);

    <T> Object retrieve(Class<T> clase, String id);

    <T> List<T> retrieve(Class<T> clase);
}
