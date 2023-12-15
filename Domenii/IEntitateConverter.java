package Domenii;

public interface IEntitateConverter <T extends Entitate>{
    String toString(T object);

    T fromString(String line);
}
