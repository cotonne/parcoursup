package parcoursup.ordreappel.algo;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TauxBoursier that = (TauxBoursier) o;
        return tauxEnPourcentage == that.tauxEnPourcentage;
    }

    @Override
    public int hashCode() {

        return Objects.hash(tauxEnPourcentage);
    }
}
