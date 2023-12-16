package Repository;
import Domenii.Entitate;

import java.io.*;
import java.util.ArrayList;

public class BinaryRepository<T extends Entitate> extends Repository<T> {
    private String fileName;

    public BinaryRepository(String fileName) {
//        super();
        this.fileName = fileName;
        try {
            loadFile();
        } catch (IOException | ClassNotFoundException e) {
        }
    }


    @Override
    public void add(T o) throws DuplicateEntityException, IOException {
        super.add(o);
        // saveFile se executa doar daca super.add() nu a aruncat exceptie
        try {
            saveFile();
        } catch (IOException e) {
            throw new DuplicateEntityException("Error saving file " + e.getMessage());
        }
    }

    @Override
    public void remove(int id) throws IOException {
        super.remove(id);
        saveFile();
    }

    @Override
    public void update(T e) throws IOException {
        super.update(e);
        saveFile();
    }

    private void loadFile() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = null;
            ois = new ObjectInputStream(new FileInputStream(fileName));
            this.entities = (ArrayList<T>) ois.readObject();
            ois.close();
    }


    private void saveFile() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
            oos.writeObject(entities);
    }

}
