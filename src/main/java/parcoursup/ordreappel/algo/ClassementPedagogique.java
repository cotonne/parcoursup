package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ClassementPedagogique {
    public final List<Eleve> eleves;

    public ClassementPedagogique(Eleve... eleves) {
        this.eleves = Collections.unmodifiableList(Arrays.asList(eleves));
    }

    public ClassementPedagogique(List<Eleve> eleves) {
        this.eleves = eleves;
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

    private Pair<Eleve, ClassementPedagogique> prendreSelonStatusBourse(StatusBourse nonBoursier) {
        Eleve head = eleves.stream().filter(e -> e.hasStatus(nonBoursier)).findFirst().orElseThrow(() -> new IllegalStateException("Plus de boursiers"));
        List<Eleve> tail = new ArrayList<>(eleves);
        tail.remove(head);
        return Pair.of(head, new ClassementPedagogique(tail));
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivant() {
        Eleve head = eleves.get(0);
        List<Eleve> tail = eleves.subList(1, eleves.size());
        return Pair.of(head, new ClassementPedagogique(tail));
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivantSelon(StatusBourse selection) {
        return prendreSelonStatusBourse(selection);
    }

    boolean aDesPostulants() {
        return !eleves.isEmpty();
    }

    boolean aDesNonBoursiers() {
        return !eleves.stream().allMatch(Eleve::isBoursier);
    }

}
