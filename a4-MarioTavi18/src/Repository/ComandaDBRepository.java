package Repository;

import Domenii.Comanda;
import Domenii.Tort;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class ComandaDBRepository extends Repository<Comanda> implements IDBRepository<Comanda>{
    private String JDBC_URL ="jdbc:sqlite:databaseTC.db";

    private Connection connection;

    public ComandaDBRepository() throws DuplicateEntityException, IOException {
        openConnection();
        createTable();
        //initTable();
        for(Comanda t:getAll()){super.add(t);}
    }

    public void openConnection(){
        SQLiteDataSource ds=new SQLiteDataSource();
        ds.setUrl(JDBC_URL);


        try {
            if (connection == null || connection.isClosed()){
                connection=ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void closeConnection(){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void createTable(){
        try (final Statement stat = connection.createStatement()) {
            stat.execute("CREATE TABLE IF NOT EXISTS Comanda(id int,data String,torturi String);");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public Date DataRandom() throws ParseException {
        StringBuilder dataString=new StringBuilder();
        Random rand=new Random();
        int d=rand.nextInt(31 - 1 + 1) + 1;
        dataString.append(d).append("/");
        d=rand.nextInt(12 - 1 + 1) + 1;
        dataString.append(d).append("/");
        d=rand.nextInt(2023 - 2000 + 1) + 2000;
        dataString.append(d);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date dataC=sdf.parse(dataString.toString());
        return dataC;
    }
    public Tort TortRandom(ArrayList<Tort> torturi){
        Random rand=new Random();
        int indexTort=rand.nextInt(100);
        return torturi.get(indexTort);
    }

    public void Generam(ArrayList<Comanda> l) throws DuplicateEntityException, IOException, ParseException {
        TortDBRepository tdb=new TortDBRepository();
        ArrayList<Tort> torturi=tdb.getAll();
        for(int i=0;i<100;i++){
            Random rand=new Random();
            int nrTorturi=rand.nextInt(3 - 1 + 1) + 1;
            ArrayList<Tort> torturiC=new ArrayList<>();
            for(int j=0;j<nrTorturi;j++){
                torturiC.add(TortRandom(torturi));
            }
            Comanda to=new Comanda(i+1,torturiC,DataRandom());
            //l.add(to);
        }
    }
    public void initTable(){
        ArrayList<Comanda> Comenzi=new ArrayList<>();
        try {
            Generam(Comenzi);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try(PreparedStatement stat=connection.prepareStatement("INSERT INTO Comanda VALUES(?,?,?)")){
            for(Comanda t : Comenzi){
                stat.setInt(1,t.getId());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                stat.setString(2,formatter.format(t.getData()));
                String serializedTortList = serializeTortList(t.getTorturi());
                stat.setObject(3, serializedTortList);
                stat.executeUpdate();
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Comanda> getAll(){
        ArrayList<Comanda> lista=new ArrayList<>();
        try(PreparedStatement stat=connection.prepareStatement("SELECT * FROM Comanda;")){
            ResultSet rs=stat.executeQuery();
            while(rs.next()){
                String serializedTortList = rs.getString("torturi");
                ArrayList<Tort> torturi=deserializeTortList(serializedTortList);

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String data=rs.getString(2);
                java.util.Date dataC=sdf.parse(data);

                Comanda t=new Comanda(rs.getInt(1), torturi, dataC);
                lista.add(t);
            }
        }
        catch(SQLException | ParseException e){
            e.printStackTrace();
        }

        return lista;
    }

    public void add(Comanda t) throws DuplicateEntityException, IOException {
        super.add(t);
        try(PreparedStatement stat=connection.prepareStatement("INSERT INTO Comanda VALUES(?,?,?)")){
            stat.setInt(1,t.getId());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            stat.setString(2,formatter.format(t.getData()));
            String serializedTortList = serializeTortList(t.getTorturi());
            stat.setObject(3, serializedTortList);
            stat.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void update(Comanda t) throws IOException {
        super.update(t);
        try(PreparedStatement stat=connection.prepareStatement("UPDATE Comanda SET id=?,data=?,torturi=? WHERE id=?")){
            stat.setInt(1,t.getId());
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            stat.setString(2,formatter.format(t.getData()));
            String serializedTortList = serializeTortList(t.getTorturi());
            stat.setString(3,serializedTortList);
            stat.setInt(4,t.getId());
            stat.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void remove(int id) throws IOException {
        super.remove(id);
        try(PreparedStatement stat=connection.prepareStatement("DELETE FROM Tort WHERE id=?")){
            stat.setInt(1,id);
            stat.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public final String serializeTortList(ArrayList<Tort> lista){
        StringBuilder st = new StringBuilder();
        for (Tort tort : lista) {
            st.append(tort.getId()).append(",").append(tort.getTip()).append(";");
        }
        return st.toString();
    }
    public ArrayList<Tort> deserializeTortList(String lista){
        //1,Cioco;2,caramel;3,caramel2;....
        int nr=(lista.split(";").length);
        String[] ent=lista.split(";");
        ArrayList<Tort> torturi=new ArrayList<>();
        for(int i=0;i<nr;i++){
            int id=Integer.parseInt(ent[i].split(",")[0]);
            String tip=ent[i].split(",")[1];
            Tort t=new Tort(id,tip);
            torturi.add(t);
        }
        return torturi;
    }
    ///pt generare 100 comenzi: dai random nr=[1,3] pt cate torturi are fiecare si te duci
    ///cu alt random sa iei cu findByID torturi
}
