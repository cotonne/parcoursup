package parcoursup.ordreappel.algo;

public class TauxBoursier {
    public static final TauxBoursier ZERO = new TauxBoursier(0);
    private final long tauxEnPourcentage;

    public TauxBoursier(long tauxEnPourcentage) {
        this.tauxEnPourcentage = tauxEnPourcentage;
    }

    @Override
    public String toString() {
        return "TauxBoursier{" +
                "tauxEnPourcentage=" + tauxEnPourcentage +
                '}';
    }

    public boolean estMoinsQue(TauxBoursier tauxBoursier) {
        return tauxEnPourcentage <= tauxBoursier.tauxEnPourcentage;
    }
}
