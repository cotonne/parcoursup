package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassementPedagogique {
    final List<Eleve> eleves;

    ClassementPedagogique(Eleve... eleves) {
        this.eleves = Collections.unmodifiableList(Arrays.asList(eleves));
    }

    @Override
    public String toString() {
        return "ClassementPedagogique{" +
                "eleves=" + eleves +
                '}';
    }

    boolean aDesBoursiers() {
        return eleves.stream().anyMatch(Eleve::isBoursier);
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivant() {
        Eleve head = eleves.get(0);
        List<Eleve> tail = eleves.subList(1, eleves.size());
        return Pair.of(head, new ClassementPedagogique(tail.toArray(new Eleve[0])));
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivantSelon(StatusAvecPriorite selection) {
        Eleve head = eleves.stream().filter(e -> e.hasStatus(selection)).findFirst().orElseThrow(() -> new IllegalStateException("Plus de boursiers"));
        List<Eleve> tail = new ArrayList<>(eleves);
        tail.remove(head);
        return Pair.of(head, new ClassementPedagogique(tail.toArray(new Eleve[0])));
    }

    boolean aDesPostulants() {
        return !eleves.isEmpty();
    }

    public boolean a(StatusAvecPriorite statusAvecPriorite) {
        return eleves.stream().anyMatch(e -> e.hasStatus(statusAvecPriorite));
    }
}
