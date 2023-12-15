package test;

import Domenii.Comanda;
import Domenii.ComandaFactory;
import Domenii.Tort;
import Domenii.TortFactory;
import Repository.*;
import Service.ComandaService;
import Service.TortService;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class teste {

    @Test
    public void TesteEntitati() throws ParseException {
        Tort t=new Tort(1,"ceva");

        assert t.getId()==1;
        assert "ceva".equals(t.getTip());
        t.setTip("ceva_nou");
        assert "ceva_nou".equals(t.getTip());

        ArrayList<Tort> l=new ArrayList<>();
        l.add(t);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("11/11/2000");
        Comanda c=new Comanda(1,l, date1);
        assert c.GetId()==1;
        assert c.getTorturi().size()==1;
        assert "ceva_nou".equals(c.getTorturi().get(0).getTip());
        ArrayList<Tort> l2=new ArrayList<>();
        l2.add(t);
        l2.add(t);
        c.setTorturi(l2);
        assert c.getTorturi().size()==2;
    }
    @Test
    public void TesteFileRepoServ() throws DuplicateEntityException, IOException, ParseException {
        IRepository<Tort> repoT= new FileRepository("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileTort.txt", new TortFactory());
        IRepository<Comanda> repoC= new FileRepository("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileComanda.txt", new ComandaFactory());

        TortService sT = new TortService(repoT);
        ComandaService sC = new ComandaService(repoC);

        //adaugam al doilea tort
        sT.add(2,"ciocolata");
        try {
            sT.add(1,"vanilie");
            assert false;
        }
        catch (DuplicateEntityException e){
            assert true;
        }
        try {
            Tort e=null;
            repoT.add(e);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = sdf.parse("11/11/2000");
        //testare lista de torturi pt comanda
        ArrayList<Tort> lista = new ArrayList<>();
        lista.add(sT.GetByID(1));
        sC.add(1,lista,date1);
        assert lista.isEmpty() == false;
        assert lista.size()==1;

        //testare update tort si remove
        sT.update(2,"SCHIMBAT");
        List<Tort> torturi= (List<Tort>) sT.GetAll();
        assert "vanilie".equals(torturi.get(0).getTip());
        assert "SCHIMBAT".equals(torturi.get(1).getTip());
        sT.remove(2);
        assert sT.GetAll().size()==1;
        try {
            sT.remove(10);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }

        //testare update comanda si remove
        List<Comanda> comenzi= (List<Comanda>) sC.GetAll();
        assert comenzi.get(0).getTorturi().size()==1;

        lista.add(new Tort(2,"ceva"));
        sC.update(1,lista,date1);

        List<Comanda> comenzi2= (List<Comanda>) sC.GetAll();
        assert comenzi2.get(0).getTorturi().size()==2;

        sC.remove(1);
        assert sC.GetAll().size()==0;

        try {
            sC.update(10,null,date1);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }

        try {
            sC.remove(10);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }


        ///Files to default case
        FileWriter fr = new FileWriter("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileTort.txt");
        fr.write("1,vanilie");
        fr.close();
        fr = new FileWriter("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileComanda.txt");
        fr.write("");
        fr.close();
    }
    @Test
    public void TesteBinaryRepoServ() throws DuplicateEntityException, IOException, ParseException {
        IRepository<Tort> repoT= new BinaryRepository("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileTort.bin");
        IRepository<Comanda> repoC= new BinaryRepository("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileComanda.bin");

        TortService sT = new TortService(repoT);
        ComandaService sC = new ComandaService(repoC);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = sdf.parse("11/11/2000");
        //adaugam torturi
        sT.add(1,"vanilie");
        sT.add(2,"ciocolata");
        try {
            sT.add(1,"vanilie");
            assert false;
        }
        catch (DuplicateEntityException e){
            assert true;
        }
        try {
            Tort e=null;
            repoT.add(e);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }

        //testare lista de torturi pt comanda
        ArrayList<Tort> lista = new ArrayList<>();
        lista.add(sT.GetByID(1));
        sC.add(1,lista,data);
        assert lista.isEmpty() == false;
        assert lista.size()==1;

        //testare update tort si remove
        sT.update(2,"SCHIMBAT");
        List<Tort> torturi= (List<Tort>) sT.GetAll();
        assert "vanilie".equals(torturi.get(0).getTip());
        assert "SCHIMBAT".equals(torturi.get(1).getTip());
        sT.remove(2);
        assert sT.GetAll().size()==1;
        try {
            sT.remove(10);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }

        //testare update comanda si remove
        List<Comanda> comenzi= (List<Comanda>) sC.GetAll();
        assert comenzi.get(0).getTorturi().size()==1;

        lista.add(new Tort(2,"ceva"));
        sC.update(1,lista,data);

        List<Comanda> comenzi2= (List<Comanda>) sC.GetAll();
        assert comenzi2.get(0).getTorturi().size()==2;

        sC.remove(1);
        assert sC.GetAll().size()==0;

        try {
            sC.update(10,null,data);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }

        try {
            sC.remove(10);
            assert false;
        }
        catch (IllegalArgumentException e){
            assert true;
        }


        ///Files to default case
        FileWriter fr = new FileWriter("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileTort.bin");
        fr.close();
        fr = new FileWriter("C:\\Users\\mario\\Desktop\\MAP\\a2-MarioTavi18\\test\\testFileComanda.bin");
        fr.write("");
        fr.close();
    }

    @Test
    public void TesteRepo() throws DuplicateEntityException, IOException, ParseException {
        IRepository<Tort> repoT= new Repository<Tort>();
        IRepository<Comanda> repoC= new Repository<Comanda>();
        Tort t1=new Tort(1,"cocos");
        Tort t2=new Tort(2,"vanilie");
        repoT.add(t1);
        repoT.add(t2);
        ArrayList<Tort> lista1= (ArrayList<Tort>) repoT.getAll();
        assert "cocos".equals(lista1.get(0).getTip());
        assert "vanilie".equals(lista1.get(1).getTip());

        Tort t3=new Tort(2,"SCHIMBAT");
        repoT.update(t3);

        ArrayList<Tort> lista2= (ArrayList<Tort>) repoT.getAll();
        assert "SCHIMBAT".equals(lista2.get(1).getTip());

        repoT.remove(2);
        assert repoT.getAll().size()==1;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date data = sdf.parse("11/11/2000");

        Comanda c1=new Comanda(1,lista1, data);
        repoC.add(c1);
        ArrayList<Comanda> comenzi1= (ArrayList<Comanda>) repoC.getAll();
        assert comenzi1.get(0).getTorturi().size()==2;
        assert comenzi1.size()==1;

        Comanda c2=new Comanda(1, (ArrayList<Tort>) repoT.getAll(), data);
        repoC.update(c2);
        ArrayList<Comanda> comenzi2= (ArrayList<Comanda>) repoC.getAll();
        assert comenzi2.get(0).getTorturi().size()==1;
        assert comenzi2.size()==1;

        repoC.remove(1);
        assert repoC.getAll().size()==0;
    }
}
