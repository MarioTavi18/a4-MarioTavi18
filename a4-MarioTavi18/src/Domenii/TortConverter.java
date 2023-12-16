package Domenii;

public class TortConverter implements IEntitateConverter<Tort> {
    public String toString(Tort object) {
        return object.getId() + "," + object.getTip();
    }

    @Override
    public Tort fromString(String line) {
        String[] tokens = line.split(",");
        return new Tort(Integer.parseInt(tokens[0]),tokens[1]);
    }
}
