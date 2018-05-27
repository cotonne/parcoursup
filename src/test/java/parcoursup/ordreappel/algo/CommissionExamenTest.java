package parcoursup.ordreappel.algo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CommissionExamenTest {

    // [P], 0 place, aucun refus => []
    // [P], 1 place, aucun refus  => [P]
    // [P1, P2], 2 places, aucun refus => [P1, P2]
    // [P1, P2, P3], 2 places, aucun refus => [P1, P2]
    // [P1, B1], 1 places, qB = 1%, aucun refus => [B1]
    // [P1, B1], 1 places, qB = 1%, refus de B1 => [P1]

    @Test
    public void ne_devrait_faire_aucune_proposition_pour_une_formation_sans_place() {
        Eleve eleve = Eleve.nonBoursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleve);

        Formation formation = new Formation(0);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(0));

        assertThat(ordreAppel).isEqualTo(new OrdreAppel());
    }

    @Test
    public void devrait_proposer_au_seul_postulant_la_seule_place_de_la_formation() {
        Eleve eleve = Eleve.nonBoursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleve);

        Formation formation = new Formation(1);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(0));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleve));
    }

    @Test
    public void devrait_proposer_des_places_tant_qu_il_y_en_a() {
        Eleve eleve1 = Eleve.nonBoursier();
        Eleve eleve2 = Eleve.nonBoursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleve1, eleve2);

        Formation formation = new Formation(2);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(0));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleve1, eleve2));
    }

    @Test
    public void devrait_ne_pas_faire_plus_de_proposition_qu_il_n_y_a_de_places() {
        Eleve eleve1 = Eleve.nonBoursier();
        Eleve eleve2 = Eleve.nonBoursier();
        Eleve eleve3 = Eleve.nonBoursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleve1, eleve2, eleve3);

        Formation formation = new Formation(2);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(0));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleve1, eleve2));
    }

    @Test
    public void devrait_proposer_en_priorité_aux_boursiers() {
        Eleve eleveNonBoursier = Eleve.nonBoursier();
        Eleve eleveBoursier = Eleve.boursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleveNonBoursier, eleveBoursier);

        Formation formation = new Formation(1);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(1));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleveBoursier));
    }

    // [P1, B1, B2], 3 places, qB = 1%, aucun refus => [B1, P1, B2]
    // [P1, B1, P2], 1 places, qB = 0%, aucun refus => [P1]

    @Test
    public void devrait_conserver_la_proportion_de_boursiers_à_chaque_rang() {
        Eleve eleveNonBoursier = Eleve.nonBoursier();
        Eleve eleveBoursier1 = Eleve.boursier();
        Eleve eleveBoursier2 = Eleve.boursier();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleveNonBoursier, eleveBoursier1, eleveBoursier2);

        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerSelon(classementPedagogique, new TauxBoursier(1));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleveBoursier1, eleveNonBoursier, eleveBoursier2));
    }


    @Test
    public void devrait_conserver_l_ordre_tant_que_le_taux_est_respecté() {
        Eleve[] eleves = {Eleve.boursier(), Eleve.nonBoursier()};
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleves);

        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerParCritères(classementPedagogique, TauxBoursier.ZERO);

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleves));
    }

    @Test
    public void cas_avec_que_des_non_boursiers() {
        Eleve[] eleves = {Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier()};
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleves);

        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerParCritères(classementPedagogique, new TauxBoursier(3));

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleves));
    }

    @Test
    public void XXX() {
        // 0
        //[Eleve{id=parcoursup.ordreappel.algo.Eleve@2e407b6b, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@473f1c9f, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@4724bef, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@5aa19387, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@7717209b, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@48f31102, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@5e4806c0, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@8238b25, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@46fe7142, statusBourse=NON_BOURSIER}]


        //[Eleve{id=parcoursup.ordreappel.algo.Eleve@2e407b6b, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@473f1c9f, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@4724bef, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@5aa19387, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@7717209b, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@48f31102, statusBourse=NON_BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@5e4806c0, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@8238b25, statusBourse=BOURSIER},
        // Eleve{id=parcoursup.ordreappel.algo.Eleve@46fe7142, statusBourse=NON_BOURSIER}]
        Eleve[] eleves = {Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier(), Eleve.nonBoursier()};
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleves);

        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerParCritères(classementPedagogique, TauxBoursier.ZERO);

        assertThat(ordreAppel).isEqualTo(OrdreAppel.de(eleves));
    }

    // Tests d'acceptances définis dans l'annexe A.1 de presentation_algorithmes_parcoursup.pdf
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20% => Ordre d'appel : [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20%, refus de B1 => [C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [C1, C2, C3, C4, C5, B6, C7, C8, C9, C10, C11], 1 place, qB = 2% => [B6]
    // [C1 C2 C3 ...C900 B901 B902 B903 ...B1000 C1001 C1002 ....], 100 places, qB = 10% => [B901 C1 C2 C3 ...C9 B902 C10 C11 ...C18 B903 ...] (propositionAdmission.pourcentageBoursier >= qB)
    // [C1 B2 B3 C4 C5 C6 C7 B8 C9 C10], 1 place, qB = 20% => Ordre d'appel [B2 C1 B3 C4 C5 C6 C7 B8 C9 C10] [ne fait jamais reculer le classement d’un boursier]

}