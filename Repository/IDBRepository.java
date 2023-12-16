package Repository;

import Domenii.Entitate;

public interface IDBRepository <T extends Entitate> extends IRepository<T> {
    void openConnection();
    void closeConnection();
    void createTable();
    void initTable();

}
