package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel ordonnerSelon(ClassementPedagogique classementPedagogique, TauxBoursier tauxBoursier) {
        OrdreAppel ordreAppel = ordonnerParCritères(classementPedagogique, tauxBoursier);
        return limiterParPlaceFormation(ordreAppel);
    }

    OrdreAppel ordonnerParCritères(ClassementPedagogique classementPedagogique, TauxBoursier tauxBoursier) {
        ClassementPedagogique classementPedagogiqueCourant = classementPedagogique;
        OrdreAppel ordreAppel = new OrdreAppel();
        while (classementPedagogiqueCourant.aDesPostulants()) {
            Pair<Eleve, ClassementPedagogique> pair = choisir(tauxBoursier, classementPedagogiqueCourant, ordreAppel);

            Eleve nouveauSelectionne = pair.getLeft();
            ordreAppel = ordreAppel.ajouter(nouveauSelectionne);
            classementPedagogiqueCourant = pair.getRight();
        }

        return ordreAppel;
    }

    private Pair<Eleve, ClassementPedagogique> choisir(TauxBoursier tauxBoursier, ClassementPedagogique classementPedagogique, OrdreAppel ordreAppel) {
        Pair<Eleve, ClassementPedagogique> pair;
        if (ordreAppel.respecte(tauxBoursier)) {
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
