package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel ordonnerSelon(ClassementPedagogique classementPedagogique, Pair<StatusAvecPriorite, Taux>... contrainte) {
        OrdreAppel ordreAppel = ordonnerParCritères(classementPedagogique, contrainte);
        return limiterParPlaceFormation(ordreAppel);
    }

    OrdreAppel ordonnerParCritères(ClassementPedagogique classementPedagogique, Pair<StatusAvecPriorite, Taux>... contrainte) {
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

    private Pair<Eleve, ClassementPedagogique> choisir(ClassementPedagogique classementPedagogique, OrdreAppel ordreAppel, Pair<StatusAvecPriorite, Taux>... contrainte) {
        Pair<StatusAvecPriorite, Taux> uneContrainte = contrainte[0];
        Taux taux = uneContrainte.getValue();
        StatusAvecPriorite statusAvecPriorite = uneContrainte.getKey();

        if (ordreAppel.respecte(taux, e -> e.hasStatus(statusAvecPriorite))) {
            return classementPedagogique.prendreSuivant();
        }

        StatusAvecPriorite status;
        if (classementPedagogique.a(statusAvecPriorite)) {
            status = statusAvecPriorite;
        } else {
            status = statusAvecPriorite.nonPrioritaire();
        }
        return classementPedagogique.prendreSuivantSelon(status);
    }

    private OrdreAppel limiterParPlaceFormation(OrdreAppel ordreAppel) {
        return ordreAppel.limit(formationProposée.nombrePlace);
    }
}
