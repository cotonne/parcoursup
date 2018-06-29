package parcoursup.ordreappel.algo;

public class Eleve {
    private final StatusBourse statusBourse;
    private final StatusResident statusResident;

    private Eleve(StatusBourse statusBourse, StatusResident statusResident) {
        this.statusBourse = statusBourse;
        this.statusResident = statusResident;
    }

    static Eleve nonBoursier() {
        return new Eleve(StatusBourse.NON_BOURSIER, StatusResident.RESIDENT);
    }

    public static Eleve boursier() {
        return new Eleve(StatusBourse.BOURSIER, StatusResident.RESIDENT);
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "statusBourse=" + statusBourse +
                ", statusResident=" + statusResident +
                '}';
    }

    public static Eleve resident() {
        return new Eleve(StatusBourse.NON_BOURSIER, StatusResident.RESIDENT);
    }

    public static Eleve nonResident() {
        return new Eleve(StatusBourse.NON_BOURSIER, StatusResident.NON_RESIDENT);
    }

    public boolean isBoursier() {
        return statusBourse == StatusBourse.BOURSIER;
    }

    boolean hasStatus(StatusAvecPriorite status) {
        return this.statusBourse == status || this.statusResident == status;
    }
}
