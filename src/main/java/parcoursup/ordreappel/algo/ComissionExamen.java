package parcoursup.ordreappel.algo;

public class ComissionExamen {
    private final Formation formationProposée;

    public ComissionExamen(Formation formationProposée) {
        this.formationProposée = formationProposée;
    }

    public OrdreAppel trie(Postulants postulants) {
        if (formationProposée.nombrePlace > 0) {
            return OrdreAppel.de(postulants.eleves);
        }
        return new OrdreAppel();
    }
}
