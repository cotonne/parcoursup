package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel ordonnerSelon(ClassementPedagogique classementPedagogique, Pair<StatusBourse, Taux>... contrainte) {
        OrdreAppel ordreAppel = ordonnerParCritères(classementPedagogique, contrainte);
        return limiterParPlaceFormation(ordreAppel);
    }

    OrdreAppel ordonnerParCritères(ClassementPedagogique classementPedagogique, Pair<StatusBourse, Taux>... contrainte) {
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

    private Pair<Eleve, ClassementPedagogique> choisir(ClassementPedagogique classementPedagogique, OrdreAppel ordreAppel, Pair<StatusBourse, Taux>... contrainte) {
        Pair<Eleve, ClassementPedagogique> pair;
        Taux taux = contrainte[0].getValue();
        if (ordreAppel.respecte(taux)) {
            pair = classementPedagogique.prendreSuivant();
        } else {
            StatusBourse statusBourse;
            if (classementPedagogique.aDesBoursiers()) {
                statusBourse = StatusBourse.BOURSIER;
            } else {
                statusBourse = StatusBourse.NON_BOURSIER;
            }
            pair = classementPedagogique.prendreSuivantSelon(statusBourse);
        }
        return pair;
    }

    private OrdreAppel limiterParPlaceFormation(OrdreAppel ordreAppel) {
        return ordreAppel.limit(formationProposée.nombrePlace);
    }
}
