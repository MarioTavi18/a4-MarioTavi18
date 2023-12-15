package Repository;

import Domenii.Entitate;

import java.io.IOException;
import java.util.Collection;

public interface IRepository<T extends Entitate> extends Iterable<T>{
    public void add(T e) throws DuplicateEntityException, IOException;
    public void update(T e) throws IOException;
    public void remove(int id) throws IOException;
    public T find (int id) throws IOException;
    public Collection<T> getAll() throws IOException;


}
