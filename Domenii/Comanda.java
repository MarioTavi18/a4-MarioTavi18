package Domenii;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Comanda extends Entitate implements Serializable {

    private ArrayList<Tort> torturi;
    private Date data;
    private static final long serialVersionUID = 1000L;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Tort s : torturi)
            sb.append("  ").append(s).append('\n');
        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        return "Comanda:" + "id=" + id + ",din data:"+formatter.format(data)+"\nTorturile din comanda sunt:\n"+sb;
    }

    public int GetId(){return this.id;}

    public Date getData() {
        return this.data;
    }

    public ArrayList<Tort> getTorturi() {
        return this.torturi;
    }

    public void setTorturi(ArrayList<Tort> torturi) {
        this.torturi = torturi;
    }

    public Comanda(int i, ArrayList<Tort> t, Date d) {
        super(i);
        this.torturi = t;
        this.data = d;
    }
}
