package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static java.util.stream.Collectors.toList;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassementPedagogique that = (ClassementPedagogique) o;
        return Objects.equals(eleves, that.eleves);
    }

    @Override
    public int hashCode() {

        return Objects.hash(eleves);
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivant() {
        Eleve head = eleves.get(0);
        List<Eleve> tail = eleves.subList(1, eleves.size());
        return Pair.of(head, new ClassementPedagogique(tail.toArray(new Eleve[0])));
    }

    Pair<Eleve, ClassementPedagogique> prendreSuivantSelon(List<StatusAvecPriorite> contraintesNonSatistfaites) {
        List<Eleve> elevesClassesParPrioriteSatisfaisantToutesLesContraintes = eleves.stream()
                .filter(e -> contraintesNonSatistfaites.stream().anyMatch(e::hasStatus))
                .sorted(Comparator.comparingLong(Eleve::getPriorite).reversed())
                .collect(toList());
        Eleve head;
        if (elevesClassesParPrioriteSatisfaisantToutesLesContraintes.isEmpty())
            head = eleves.get(0);
        else
            head = elevesClassesParPrioriteSatisfaisantToutesLesContraintes.get(0);

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

    Pair<Eleve, ClassementPedagogique> prendreSuivantRespectant(List<StatusAvecPriorite> contraintesNonSatisfaites) {
        if (!contraintesNonSatisfaites.isEmpty()) {
            return prendreSuivantSelon(contraintesNonSatisfaites);
        }
        return prendreSuivant();
    }
}
