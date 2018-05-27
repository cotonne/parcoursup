package parcoursup.ordreappel.algo;

public class Eleve {
    public final StatusBourse statusBourse;

    protected Eleve(StatusBourse statusBourse) {
        this.statusBourse = statusBourse;
    }

    public Eleve() {
        statusBourse = null;
    }

    public static Eleve nonBoursier() {
        return new Eleve(StatusBourse.NON_BOURSIER);
    }

    public static Eleve boursier() {
        return new Eleve(StatusBourse.BOURSIER);
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "id=" + super.toString() + ", " +
                "statusBourse=" + statusBourse +
                '}';
    }

    public StatusBourse getStatusBourse() {
        return statusBourse;
    }

    public boolean isBoursier() {
        return statusBourse == StatusBourse.BOURSIER;
    }
}
