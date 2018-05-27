package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

public class Groupe {
    private final List<Eleve> eleves;

    public Groupe(List<Eleve> eleves) {
        this.eleves = eleves;
    }

    boolean aDesBoursiers() {
        return eleves.stream().anyMatch(Eleve::isBoursier);
    }

    private Pair<Eleve, Groupe> retirerPremierBoursier() {
        Eleve head = eleves.stream().filter(Eleve::isBoursier).findFirst().orElseThrow(() -> new IllegalStateException("Plus de boursiers"));
        List<Eleve> tail = new ArrayList<>(eleves);
        tail.remove(head);
        return Pair.of(head, new Groupe(tail));
    }

    private Pair<Eleve, Groupe> retirerPremierNonBoursier() {
        Eleve head = eleves.stream().filter(x -> !x.isBoursier()).findFirst().orElseThrow(() -> new IllegalStateException("Plus de boursiers"));
        List<Eleve> tail = new ArrayList<>(eleves);
        tail.remove(head);
        return Pair.of(head, new Groupe(tail));
    }

    public Pair<Eleve, Groupe> prendreSuivant() {
        Eleve head = eleves.get(0);
        List<Eleve> tail = eleves.subList(1, eleves.size());
        return Pair.of(head, new Groupe(tail));
    }

    Pair<Eleve, Groupe> prendreSelon(StatusBourse selection) {
        Pair<Eleve, Groupe> pair;
        if (selection == StatusBourse.BOURSIER) {
            pair = retirerPremierBoursier();
        } else {
            pair = retirerPremierNonBoursier();
        }
        return pair;
    }

    boolean aDesPostulants() {
        return aDesBoursiers() || aDesNonBoursiers();
    }

    boolean aDesNonBoursiers() {
        return !eleves.stream().allMatch(Eleve::isBoursier);
    }
}
