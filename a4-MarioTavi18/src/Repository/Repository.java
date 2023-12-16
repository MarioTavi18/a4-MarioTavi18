package Repository;

import Domenii.Entitate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Repository <T extends Entitate> implements IRepository<T> {
    List<T> entities = new ArrayList<T>();
    @Override
    public void add(T e) throws DuplicateEntityException, IOException {
        if (e == null){
            throw new IllegalArgumentException("Entitatea nu poate fi nulla!");
        }
        if (find(e.getId())!=null){
            throw new DuplicateEntityException("Entitatea deja exista!");
        }
        entities.add(e);
    }

    @Override
    public void update(T e) throws IOException {
        if(find(e.getId())== null) throw new IllegalArgumentException("Entitatea nu exista!");
        int nr=0;
        for (T ent: entities)
        {
            if(ent.getId()==e.getId())
            {
                entities.set(nr,e);
                return;
            }
            nr++;
        }
    }

    @Override
    public void remove(int id) throws IOException {
        if (find(id)==null) throw new IllegalArgumentException("Entitatea nu exista!");
        for (T ent: entities)
        {
            if(ent.getId()==id)
            {
                entities.remove(ent);
                return;
            }
        }

    }

    @Override
    public T find(int id) throws IOException {
        for (T ent: entities)
        {
            if(ent.getId()==id)
            {
                return ent;
            }
        }
        return null;
    }

    @Override
    public Collection<T> getAll() throws IOException {
        return new ArrayList<T>(entities) ;
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayList<T>(entities).iterator();
    }


}
