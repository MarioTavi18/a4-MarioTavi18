package UI;

import Domenii.Comanda;
import Domenii.Tort;
import Repository.DuplicateEntityException;
import Service.ComandaService;
import Service.TortService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

public class Consola {
    TortService servT;
    ComandaService servC;

    public Consola(TortService servT, ComandaService servC) {
        this.servT = servT;
        this.servC = servC;
    }

    public void runMenu( ) throws IOException {
//        try {
//            servT.add(1, "ciocolata");
//            servT.add(2, "vanilie");
//            servT.add(3, "caramel");
//            servT.add(4, "cocos");
//            servT.add(5, "lapte");
//            servT.add(6, "cajuu");
//            ArrayList<Tort>t1=new ArrayList<>();
//            t1.add(servT.GetByID(1));
//            t1.add(servT.GetByID(2));
//            ArrayList<Tort>t2=new ArrayList<>();
//            t2.add(servT.GetByID(2));
//            t2.add(servT.GetByID(3));
//            servC.add(1,t1);
//            servC.add(2,t2);
//        }
//        catch (Exception e){}
        while(true)
        {
            printMenu();
            String option;
            Scanner scanner=new Scanner(System.in);
            System.out.print("Dati optiunea:");
            option = scanner.next();
            switch(option){
                case "1t": {
                    try {
                        System.out.print("Dati id-ul tortului:");
                        int id = scanner.nextInt();
                        System.out.print("Dati tipul tortului:");
                        String tip = scanner.next();
                        servT.add(id, tip);
                    } catch (DuplicateEntityException e) {
                        System.out.println(e.toString());
                    }
                      catch (Exception e){
                          System.out.println(e.toString());
                      }
                    break;
                }
                case "2t": {
                    try {
                        System.out.print("Dati id-ul tortului care sa fie schimbat:");
                        int id = scanner.nextInt();
                        System.out.print("Dati tipul tortului nou:");
                        String tip = scanner.next();
                        servT.update(id, tip);
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "3t": {
                    try {
                        System.out.print("Dati id-ul tortului care sa fie sters:");
                        int id = scanner.nextInt();
                        servT.remove(id);
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "1c": {
                    try {
                        System.out.print("Dati id-ul comenzii:");
                        int id = scanner.nextInt();
                        scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("Dati data (dd/MM/yyyy):");
                        String dataS = scanner.nextLine();
                        Date data=sdf.parse(dataS);

                        System.out.print("Dati numarul de torturi al comenzii:");
                        int nr = scanner.nextInt();
                        if(nr<=0) throw new IllegalArgumentException("Fiecare comanda trebuie sa aiba macar un tort!");

                        ArrayList<Tort> lista= new ArrayList<>();
                        for (int i=0;i<nr;i++){
                            System.out.print("Dati id-ul tortului "+ (i+1)+":");
                            int d= scanner.nextInt();
                            Tort t=servT.GetByID(d);
                            if(t==null){
                                throw new Exception("Tortul nu exista");
                            }
                            lista.add(t);
                        }
                        servC.add(id,lista,data);
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "2c": {
                    try {
                        System.out.print("Dati id-ul comenzii care sa fie schimbate:");
                        int id = scanner.nextInt();

                        scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("Dati data (dd/MM/yyyy):");
                        scanner.hasNextLine();
                        String dataS = scanner.nextLine();
                        Date data=sdf.parse(dataS);

                        System.out.print("Dati numarul de torturi al comenzii:");
                        int nr = scanner.nextInt();
                        if(nr<=0) throw new IllegalArgumentException("Fiecare comanda trebuie sa aiba macar un tort!");
                        ArrayList<Tort> lista= new ArrayList<>();

                        for (int i=0;i<nr;i++){
                            System.out.print("Dati id-ul tortului "+ (i+1)+":");
                            int d= scanner.nextInt();
                            Tort t=servT.GetByID(d);
                            if(t==null){
                                throw new Exception("Tortul nu exista");
                            }
                            lista.add(t);
                        }
                        servC.update(id,lista,data);
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "3c": {
                    try {
                        System.out.print("Dati id-ul comenzii care sa fie stearsa:");
                        int id = scanner.nextInt();
                        servC.remove(id);
                    }
                    catch (Exception e){
                        System.out.println(e.toString());
                    }
                    break;
                }
                case "t": {
                    Collection<Tort> entitati= servT.GetAll();
                    for (Tort e: entitati){
                        System.out.println(e);
                    }
                    break;
                }
                case "c": {
                    Collection<Comanda> entitati= servC.GetAll();
                    for (Comanda e: entitati){
                        System.out.println(e);
                    }
                    break;
                }
                case "x": {
                    return;
                }
                default: {
                    System.out.println("Optiune gresita! Reincercati!");
                }
            }
        }
    }

    private void printMenu(){
        System.out.println('\n'+"1t. Adauga tort");
        System.out.println("2t. Modifica tort");
        System.out.println("3t. Sterge tort\n");
        System.out.println("1c. Adauga comanda");
        System.out.println("2c. Modifica comanda");
        System.out.println("3c. Sterge comanda");
        System.out.println("\nt. Afiseaza toate torturile");
        System.out.println("c. Afiseaza toate comenzile");
        System.out.println("x. Iesire");
    }
}
