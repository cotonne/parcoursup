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

            Pair<Eleve, Groupe> pair;
            if (ordreAppel.respecte(tauxBoursier)) {
                pair = groupe.prendreSuivant();
            } else {
                StatusBourse selection = choisirUnBoursierDePreference(groupe);
                pair = groupe.prendreSelon(selection);
            }

            Eleve nouveauSelectionne = pair.getLeft();
            groupe = pair.getRight();
            ordreAppel = ordreAppel.ajouter(nouveauSelectionne);
        }

        return ordreAppel;
    }

    private StatusBourse choisirUnBoursierDePreference(Groupe groupe) {
        StatusBourse selection;
        if (groupe.aDesBoursiers() || !groupe.aDesNonBoursiers()) {
            selection = StatusBourse.BOURSIER;
        } else {
            selection = StatusBourse.NON_BOURSIER;
        }
        return selection;
    }

    private OrdreAppel limiterParPlaceFormation(OrdreAppel ordreAppel) {
        return ordreAppel.limit(formationProposée.nombrePlace);
    }
}
