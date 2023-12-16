package Service;

import Domenii.Comanda;
import Domenii.Tort;
import Repository.DuplicateEntityException;
import Repository.IRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ComandaService {
    IRepository<Comanda> repoC;
    public ComandaService(IRepository<Comanda> r){
        this.repoC=r;
    }

    public void add(int id, ArrayList<Tort> l,Date data) throws DuplicateEntityException, IOException {
        repoC.add(new Comanda(id,l, data));
    }

    public void update(int id, ArrayList<Tort> l,Date data) throws IOException {
        repoC.update(new Comanda(id,l, data));
    }
    public void remove(int id) throws IOException {
        repoC.remove(id);
    }
    public Collection<Comanda> GetAll() throws IOException {
        return repoC.getAll();
    }


}
