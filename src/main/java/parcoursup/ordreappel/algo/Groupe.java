package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Groupe {
    public final List<Eleve> boursiers;
    public final List<Eleve> nonBoursiers;

    public Groupe(List<Eleve> boursiers, List<Eleve> nonBoursiers) {
        this.boursiers = boursiers;
        this.nonBoursiers = nonBoursiers;
    }

    boolean aDesBoursiers() {
        return !boursiers.isEmpty();
    }

    private Pair<Eleve, Groupe> retirerPremierBoursier() {
        Eleve head = boursiers.get(0);
        List<Eleve> tail = boursiers.subList(1, boursiers.size());
        return Pair.of(head, new Groupe(tail, nonBoursiers));
    }

    private Pair<Eleve, Groupe> retirerPremierNonBoursier() {
        Eleve head = nonBoursiers.get(0);
        List<Eleve> tail = nonBoursiers.subList(1, nonBoursiers.size());
        return Pair.of(head, new Groupe(boursiers, tail));
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
        return !nonBoursiers.isEmpty();
    }
}
