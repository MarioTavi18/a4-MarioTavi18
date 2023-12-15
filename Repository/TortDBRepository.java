package Repository;

import Domenii.Tort;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;

public class TortDBRepository extends Repository<Tort>{
    private String JDBC_URL ="jdbc:sqlite:TortDB.db";

    private Connection connection;

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

    public void initTable(){

        ArrayList<Tort> torturi=new ArrayList<>();
        torturi.add(new Tort(1,"cioco"));
        torturi.add(new Tort(2,"cioco2"));
        torturi.add(new Tort(3,"cioco3"));
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
}
