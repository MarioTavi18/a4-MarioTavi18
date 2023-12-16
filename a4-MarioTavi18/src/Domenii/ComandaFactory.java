package Domenii;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ComandaFactory implements IEntityFactory<Comanda> {

    public Comanda createEnt(String line) throws ParseException {
        int id = Integer.parseInt(line.split(",")[0]);
        int nr = Integer.parseInt(line.split(",")[1]);
        int i;
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date data = formatter.parse(line.split(",")[2]);
        ArrayList<Tort> lista= new ArrayList<>();
        for(i=0;i<nr*2;i=i+2){
            int idT = Integer.parseInt(line.split(",")[i+3]);
            String numeT = line.split(",")[i+4];
            Tort t= new Tort(idT,numeT);
            lista.add(t);
        }
        //1,2,2,Caramel,3,d
        Comanda com=new Comanda(id,lista,data);
        return com;
    }


    public StringBuilder EntString(Comanda t) {
        StringBuilder sb = new StringBuilder();
        sb.append(t.getId()).append(",");
        ArrayList<Tort> lista=t.getTorturi();
        sb.append(lista.size()).append(",");

        SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
        sb.append(formatter.format(t.getData())).append(",");
        int nr=0;
        for(Tort tort: lista){
            nr++;
            sb.append(tort.getId()).append(",").append(tort.getTip());
            if(nr!=lista.size()){sb.append(",");}
        }
        return sb;
    }
}
