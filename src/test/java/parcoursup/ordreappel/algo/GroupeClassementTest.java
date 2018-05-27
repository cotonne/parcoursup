package parcoursup.ordreappel.algo;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GroupeClassementTest {

    // [P], 0 place, aucun refus => []
    // [P], 1 place, aucun refus  => [P]
    // [P1, P2], 2 places, aucun refus => [P1, P2]
    // [P1, P2, P3], 2 places, aucun refus => [P1, P2]
    // [P1, B1], 1 places, qB = 1%, aucun refus => [B1]
    // [P1, B1], 1 places, qB = 1%, refus de B1 => [P1]

    // Acceptances tests
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20% => Ordre d'appel : [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20%, refus de B1 => [C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [C1, C2, C3, C4, C5, B6, C7, C8, C9, C10, C11], 1 place, qB = 2% => [B6]
    // [C1 C2 C3 ...C900 B901 B902 B903 ...B1000 C1001 C1002 ....], 100 places, qB = 10% => [B901 C1 C2 C3 ...C9 B902 C10 C11 ...C18 B903 ...] (propositionAdmission.pourcentageBoursier >= qB)
    // [C1 B2 B3 C4 C5 C6 C7 B8 C9 C10], 1 place, qB = 20% => Ordre d'appel [B2 C1 B3 C4 C5 C6 C7 B8 C9 C10] [ne fait jamais reculer le classement d’un boursier]
    @Test
    public void ne_devrait_faire_aucune_proposition_pour_une_formation_sans_place() {
        Eleve eleve = new Eleve();
        Postulants postulants = new Postulants(eleve);

        Formation formation = new Formation(0);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.trie(postulants);

        assertThat(ordreAppel).isEqualTo(new OrdreAppel());
    }

    @Test
    public void devrait_proposer_au_seul_postulant_la_seule_place_de_la_formation() {
        Eleve eleve = new Eleve();
        Postulants postulants = new Postulants(eleve);

        Formation formation = new Formation(1);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.trie(postulants);

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleve));
    }
}