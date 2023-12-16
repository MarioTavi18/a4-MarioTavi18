package Domenii;

import java.io.Serializable;

public class Tort extends Entitate implements Serializable {
    private String tip;
    private static final long serialVersionUID = 10L;
    public Tort(int i, String tip) {
        super(i);
        this.tip=tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    @Override
    public String toString() {
        return  "Tort:"+"id=" + id +
                ", tip=" + tip;
    }

    public String getTip() {
        return tip;
    }
}
