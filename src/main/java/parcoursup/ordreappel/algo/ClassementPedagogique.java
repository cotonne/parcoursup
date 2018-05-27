package parcoursup.ordreappel.algo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassementPedagogique {
    public final List<Eleve> eleves;

    public ClassementPedagogique(Eleve... eleves) {
        this.eleves = Collections.unmodifiableList(Arrays.asList(eleves));
    }
}
