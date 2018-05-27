package parcoursup.ordreappel.algo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupeClassementTest {

    // [P], 0 place => []
    // [P], 1 place  => [P]
    // [P1, P2], 2 places => [P1, P2]
    // [P1, B1], 1 places, qB = 1% => [B1]

    @Test
    public void ne_devrait_faire_aucune_proposition_pour_une_formation_sans_place() {
        Eleve eleve = new Eleve();
        Postulants postulants = new Postulants(eleve);

        Formation formation = new Formation();
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.trie(postulants);

        assertThat(ordreAppel).isEqualTo(new OrdreAppel());
    }
}