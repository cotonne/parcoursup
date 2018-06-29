package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel ordonnerSelon(ClassementPedagogique classementPedagogique, Pair<StatusAvecPriorite, Taux>... contrainte) {
        OrdreAppel ordreAppel = ordonnerParCriteres(classementPedagogique, contrainte);
        return limiterParPlaceFormation(ordreAppel);
    }

    OrdreAppel ordonnerParCriteres(ClassementPedagogique classementPedagogique, Pair<StatusAvecPriorite, Taux>... contrainte) {
        ClassementPedagogique classementPedagogiqueCourant = classementPedagogique;
        OrdreAppel ordreAppel = new OrdreAppel();
        while (classementPedagogiqueCourant.aDesPostulants()) {
            Pair<Eleve, ClassementPedagogique> pair = choisir(classementPedagogiqueCourant, ordreAppel, contrainte);

            Eleve nouveauSelectionne = pair.getLeft();
            ordreAppel = ordreAppel.ajouter(nouveauSelectionne);
            classementPedagogiqueCourant = pair.getRight();
        }

        return ordreAppel;
    }

    private Pair<Eleve, ClassementPedagogique> choisir(ClassementPedagogique classementPedagogique, OrdreAppel ordreAppel, Pair<StatusAvecPriorite, Taux>... contraintes) {

        List<StatusAvecPriorite> unrespectedConstraints = Stream.of(contraintes)
                .filter(c -> !ordreAppel.respecte(c.getValue(), e -> e.hasStatus(c.getKey())))
                .map(Pair::getKey)
                .collect(Collectors.toList());

        if (unrespectedConstraints.isEmpty()) {
            return classementPedagogique.prendreSuivant();
        }

        return classementPedagogique.prendreSuivantRespectant(unrespectedConstraints);
    }

    private OrdreAppel limiterParPlaceFormation(OrdreAppel ordreAppel) {
        return ordreAppel.limit(formationProposée.nombrePlace);
    }
}
