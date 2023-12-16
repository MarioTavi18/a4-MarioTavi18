package Domenii;

import java.text.ParseException;

public interface IEntityFactory  <T extends Entitate> {
    public T createEnt(String l) throws ParseException;
    public StringBuilder EntString(T t);
}
