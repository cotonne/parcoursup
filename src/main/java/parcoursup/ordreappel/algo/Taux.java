package parcoursup.ordreappel.algo;

import java.util.Objects;

public class Taux {
    public static final Taux ZERO = new Taux(0);
    private final long tauxEnPourcentage;

    public Taux(long tauxEnPourcentage) {
        this.tauxEnPourcentage = tauxEnPourcentage;
    }

    @Override
    public String toString() {
        return "Taux{" +
                "tauxEnPourcentage=" + tauxEnPourcentage +
                '}';
    }

    public boolean estMoinsQue(Taux tauxBoursier) {
        return tauxEnPourcentage <= tauxBoursier.tauxEnPourcentage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Taux that = (Taux) o;
        return tauxEnPourcentage == that.tauxEnPourcentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tauxEnPourcentage);
    }
}
