package Domenii;

    public class TortFactory implements IEntityFactory<Tort>{

        public Tort createEnt(String line) {
            int id = Integer.parseInt(line.split(",")[0]);
            String nume = line.split(",")[1];
            Tort t= new Tort(id,nume);
            return t;
        }

        public StringBuilder EntString(Tort t) {
            StringBuilder sb = new StringBuilder();
            sb.append(t.getId()).append(",").append(t.getTip());
            return sb;
        }
}
