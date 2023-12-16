package Domenii;
import java.io.Serializable;
public class Entitate implements Serializable {
    protected int id;
    private static final long serialVersionUID = 1000L;

    public Entitate(int i){
        this.id=i;
    }

    public int getId() {
        return id;
    }
}
