package parcoursup.ordreappel.algo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassementPedagogique {
    public final List<Eleve> eleves;

    public ClassementPedagogique(Eleve... eleves) {
        this.eleves = Collections.unmodifiableList(Arrays.asList(eleves));
    }

    Groupe construire() {
        return new Groupe(eleves);
    }

    @Override
    public String toString() {
        return "ClassementPedagogique{" +
                "eleves=" + eleves +
                '}';
    }
}
