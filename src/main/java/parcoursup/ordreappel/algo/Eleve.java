package parcoursup.ordreappel.algo;

public class Eleve {
    private final StatusBourse statusBourse;

    private Eleve(StatusBourse statusBourse) {
        this.statusBourse = statusBourse;
    }

    static Eleve nonBoursier() {
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

    public boolean isBoursier() {
        return statusBourse == StatusBourse.BOURSIER;
    }

    boolean hasStatus(StatusBourse status) {
        return this.statusBourse == status;
    }
}
