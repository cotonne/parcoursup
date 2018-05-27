package parcoursup.ordreappel.algo;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel trieSelon(ClassementPedagogique classementPedagogique) {
        return OrdreAppel.de(classementPedagogique.eleves.subList(0, formationProposée.nombrePlace).toArray(new Eleve[0]));
    }
}
