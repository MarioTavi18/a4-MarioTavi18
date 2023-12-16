package Repository;

import Domenii.Entitate;
import Domenii.IEntityFactory;

import java.io.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

public class FileRepository<T extends Entitate> extends Repository<T>{
    private String file_name;
    private IEntityFactory<T> EntityFactory;
    public FileRepository(String f,IEntityFactory<T> E) throws FileNotFoundException, DuplicateEntityException, IOException, ParseException {
        this.file_name=f;
        this.EntityFactory=E;

        ReadFromFile();
    }
    private void ReadFromFile() throws FileNotFoundException, DuplicateEntityException, ParseException {
        File file= new File(file_name);
        Scanner scan= new Scanner(file);
        while(scan.hasNextLine()) {
            String line= scan.nextLine();
            T entitate= EntityFactory.createEnt(line);
            try {
                add(entitate);
            } catch (DuplicateEntityException | IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public void EnterInFile() throws IOException {
        File file= new File(file_name);
        ArrayList<T> lista= (ArrayList<T>) getAll();
        DeleteFile();
        FileWriter fr = new FileWriter(file, true);
        BufferedWriter br = new BufferedWriter(fr);
        for(T ent : lista) {
            br.write((EntityFactory.EntString(ent)).toString());
            br.newLine();
        }
        br.close();
    }

    public void DeleteFile() throws IOException {
        FileWriter fr = new FileWriter(file_name);
        fr.write("");
        fr.close();
    }
    @Override
    public void add(T e) throws DuplicateEntityException, IOException {
        super.add(e);
        EnterInFile();
    }
    @Override
    public void update(T e) throws IOException {
        super.update(e);
        EnterInFile();
    }
    @Override
    public void remove(int id) throws IOException {
        super.remove(id);
        EnterInFile();
    }
    @Override
    public T find (int id) throws IOException {
        return super.find(id);
    }
    @Override
    public Collection<T> getAll() throws IOException {
        return super.getAll();
    }
    @Override
    public Iterator<T> iterator() {
        return super.iterator();
    }
}
