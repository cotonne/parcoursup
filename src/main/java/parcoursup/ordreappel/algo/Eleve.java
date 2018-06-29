package parcoursup.ordreappel.algo;

public class Eleve implements Comparable<Eleve> {
    private final StatusBourse statusBourse;
    private final StatusResident statusResident;

    private Eleve(StatusBourse statusBourse, StatusResident statusResident) {
        this.statusBourse = statusBourse;
        this.statusResident = statusResident;
    }

    static EleveAvecStatusBoursier nonBoursier() {
        return new EleveAvecStatusBoursier(StatusBourse.NON_BOURSIER);
    }

    public static EleveAvecStatusBoursier boursier() {
        return new EleveAvecStatusBoursier(StatusBourse.BOURSIER);
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "statusBourse=" + statusBourse +
                ", statusResident=" + statusResident +
                '}';
    }

    public boolean isBoursier() {
        return statusBourse == StatusBourse.BOURSIER;
    }

    boolean hasStatus(StatusAvecPriorite status) {
        return this.statusBourse == status || this.statusResident == status;
    }

    @Override
    public int compareTo(Eleve o) {
        return Integer.compare(o.getPriorite(), this.getPriorite());
    }

    public int getPriorite() {
        return (statusBourse == StatusBourse.BOURSIER ? 2 : 0) + (statusResident == StatusResident.RESIDENT ? 1 : 0);
    }

    public static class EleveAvecStatusBoursier {
        private final StatusBourse statusBourse;

        public EleveAvecStatusBoursier(StatusBourse statusBourse) {
            this.statusBourse = statusBourse;
        }

        public Eleve resident() {
            return new Eleve(statusBourse, StatusResident.RESIDENT);
        }

        public Eleve nonResident() {
            return new Eleve(statusBourse, StatusResident.NON_RESIDENT);
        }
    }
}
