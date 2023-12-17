package Repository;

import Domenii.Tort;
import org.sqlite.SQLiteDataSource;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class TortDBRepository extends Repository<Tort> implements IDBRepository<Tort>{
    private String JDBC_URL ="jdbc:sqlite:databaseTC.db";

    private Connection connection;

    public TortDBRepository() throws DuplicateEntityException, IOException {
        openConnection();
        createTable();
        initTable();
        for(Tort t:getAll()){super.add(t);}
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
            stat.execute("CREATE TABLE IF NOT EXISTS Tort(id int,tip varchar(300));");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void Generam(ArrayList<Tort> l){
        String [] tipuri={"Vanilla", "Chocolate", "Strawberry", "Lemon", "RedVelvet", "Carrot", "Coconut", "Coffee", "Hazelnut", "Pistachio",
                "Almond", "Raspberry", "Mango", "Orange", "Cherry", "Blueberry", "Caramel", "Peppermint", "Banana", "Pineapple",
                "Blackberry", "Apple", "Ginger", "Pumpkin", "Maple", "Butterscotch", "Lavender", "Rose", "Passionfruit", "Honey",
                "Cardamom", "Chai", "EarlGrey", "Lime", "Grapefruit", "Watermelon", "Mint", "CookiesandCream", "PeanutButter",
                "WhiteChocolate", "DarkChocolate", "Tiramisu", "Matcha", "CoconutLime", "LavenderLemon", "ChocolateOrange",
                "Marble", "Mocha", "Cinnamon", "Peach", "Nutmeg", "Marshmallow", "Champagne", "Eggnog", "Grape", "Pomegranate",
                "Buttermilk", "Choco-mint", "Rosemary", "BlueRaspberry", "ButterPecan", "StrawberryShortcake", "LemonPoppyseed",
                "AlmondJoy", "KeyLime", "ChocolateMint", "RockyRoad", "S'mores", "Rum", "CoconutPineapple", "CherryAlmond",
                "BlackForest", "ChaiLatte", "Cranberry", "Brownie", "Praline", "TropicalFruit", "SaltedCaramel", "LemonBlueberry",
                "Espresso", "Gingerbread", "HoneyLavender", "PumpkinSpice", "Churro", "CherryLime", "PeachCobbler",
                "StrawberryBanana", "CinnamonRoll", "RaspberryWhiteChocolate", "BlueberryCheesecake", "CoconutMango",
                "PistachioRose", "CottonCandy", "Bubblegum", "Toffee", "ChocolateChip", "Apricot", "Plum", "Peanut", "Chestnut"};

        for(int i=0;i<100;i++){
            Tort to=new Tort(i+1,tipuri[i]);
            //l.add(to);
        }
    }
    public void initTable(){

        ArrayList<Tort> torturi=new ArrayList<>();
        Generam(torturi);
        try(PreparedStatement stat=connection.prepareStatement("INSERT INTO Tort VALUES(?,?)")){
                for(Tort t : torturi){
                    stat.setInt(1,t.getId());
                    stat.setString(2,t.getTip());
                    stat.executeUpdate();
                }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Tort> getAll(){
        ArrayList<Tort> lista=new ArrayList<>();
        try(PreparedStatement stat=connection.prepareStatement("SELECT * FROM Tort;")){
            ResultSet rs=stat.executeQuery();
            while(rs.next()){
                Tort t=new Tort(rs.getInt(1),rs.getString(2));
                lista.add(t);
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return lista;
    }

    public void add(Tort t) throws DuplicateEntityException, IOException {
        super.add(t);
        try(PreparedStatement stat=connection.prepareStatement("INSERT INTO Tort VALUES(?,?)")){
            stat.setInt(1,t.getId());
            stat.setString(2,t.getTip());
            stat.executeUpdate();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void update(Tort t) throws IOException {
        super.update(t);
        try(PreparedStatement stat=connection.prepareStatement("UPDATE Tort SET id=?,tip=? WHERE id=?")){
            stat.setInt(1,t.getId());
            stat.setString(2,t.getTip());
            stat.setInt(3,t.getId());
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
}
