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
        Groupe groupe = classementPedagogique.construire();


        OrdreAppel ordreAppel = new OrdreAppel();
        while (groupe.aDesPostulants()) {
            Pair<Eleve, Groupe> pair = choisir(tauxBoursier, groupe, ordreAppel);

            Eleve nouveauSelectionne = pair.getLeft();
            ordreAppel = ordreAppel.ajouter(nouveauSelectionne);
            groupe = pair.getRight();
        }

        return ordreAppel;
    }

    private Pair<Eleve, Groupe> choisir(TauxBoursier tauxBoursier, Groupe groupe, OrdreAppel ordreAppel) {
        Pair<Eleve, Groupe> pair;
        if (ordreAppel.respecte(tauxBoursier)) {
            pair = groupe.prendreSuivant();
        } else {
            StatusBourse selection;
            if (groupe.aDesBoursiers() || !groupe.aDesNonBoursiers()) {
                selection = StatusBourse.BOURSIER;
            } else {
                selection = StatusBourse.NON_BOURSIER;
            }
            pair = groupe.prendreSelon(selection);
        }
        return pair;
    }

    private OrdreAppel limiterParPlaceFormation(OrdreAppel ordreAppel) {
        return ordreAppel.limit(formationProposée.nombrePlace);
    }
}
