package Service;

import Domenii.Tort;
import Repository.DuplicateEntityException;
import Repository.IRepository;

import java.io.IOException;
import java.util.Collection;

public class TortService {
    IRepository<Tort> repoT;

    public TortService(IRepository<Tort> r){
        this.repoT=r;
    }

    public void add(int id, String tip) throws DuplicateEntityException, IOException {
        repoT.add(new Tort(id,tip));
    }
    public void update(int id, String tip) throws IOException {
        repoT.update(new Tort(id,tip));
    }
    public void remove(int id) throws IOException {
        repoT.remove(id);
    }
    public Collection<Tort> GetAll() throws IOException {
        return repoT.getAll();
    }

    public Tort GetByID(int id) throws IOException { return repoT.find(id);}


}
