import Domenii.Comanda;
import Domenii.ComandaFactory;
import Domenii.Tort;
import Domenii.TortFactory;
import Domenii.IEntitateConverter;
import Domenii.TortConverter;
import Repository.IRepository;
import Repository.Repository;
import Repository.DuplicateEntityException;
import Repository.FileRepository;
import Repository.BinaryRepository;
import Repository.TortDBRepository;
import Service.ComandaService;
import Service.TortService;
import UI.Consola;

import test.teste;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Objects;

public class main {
    public static void main(String[] args) throws IOException, DuplicateEntityException, ParseException {
        //IRepository<Tort> rT= new Repository<Tort>();
        //IRepository<Comanda> rC=new Repository<Comanda>();
//        IRepository<Comanda> rC=new FileRepository<>("comanda.txt",new ComandaFactory());
//        IRepository<Tort> rT= new FileRepository<>("tort.txt",new TortFactory());
//
//        IRepository<Comanda> rC=new FileRepository<>("comanda.txt",new ComandaFactory());
//        IRepository<Tort> rT= new FileRepository<>("tort.txt",new TortFactory());
//        TortService sT=new TortService(rT);
//        ComandaService sC=new ComandaService(rC);
//
//        Consola u= new Consola(sT,sC);
//
//        u.runMenu();

        teste t = new teste();
        t.TesteEntitati();
        t.TesteFileRepoServ();
        t.TesteBinaryRepoServ();
        t.TesteRepo();


        IRepository<Tort> rT = null;
        IRepository<Comanda> rC = null;
        IEntitateConverter<Tort> ec = new TortConverter();

        TortDBRepository repoDB=new TortDBRepository();
        repoDB.openConnection();
        repoDB.createTable();
        repoDB.initTable();
        repoDB.closeConnection();

        Settings setari = Settings.getInstance();
        if (Objects.equals(setari.getRepoType(), "memory")) {
            rT = new Repository<Tort>();
            rC = new Repository<Comanda>();
        }

        if (Objects.equals(setari.getRepoType(), "text")) {
            rT = new FileRepository<>(setari.getRepoFileT(), new TortFactory());
            rC = new FileRepository<>(setari.getRepoFileC(), new ComandaFactory());
        }

        if (Objects.equals(setari.getRepoType(), "binary")) {
            rT = new BinaryRepository<Tort>(setari.getRepoFileT());
            rC = new BinaryRepository<Comanda>(setari.getRepoFileC());
        }

        TortService sT=new TortService(rT);
        ComandaService sC=new ComandaService(rC);

        Consola u= new Consola(sT,sC);

        u.runMenu();

    }
    /*jdbc:sqlite:TortDB.db*/
}
