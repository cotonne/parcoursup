package parcoursup.ordreappel.algo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrdreAppelTest {

    @Test
    public void calculer_le_pourcentage_de_boursiers() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier(), Eleve.nonBoursier());

        Taux pourcentageBoursiers = ordreAppel.calculerPourcentageBoursiers(Eleve::isBoursier);

        assertThat(pourcentageBoursiers).isEqualTo(new Taux(25));
    }

    @Test
    public void calculer_le_pourcentage_de_boursiers_pour_un_ordre_vide() {
        OrdreAppel ordreAppel = OrdreAppel.de();

        Taux pourcentageBoursiers = ordreAppel.calculerPourcentageBoursiers(Eleve::isBoursier);

        assertThat(pourcentageBoursiers).isEqualTo(Taux.ZERO);
    }

    @Test
    public void indique_que_le_taux_est_respecte() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier(), Eleve.nonBoursier());

        boolean tauxRespecte = ordreAppel.respecte(new Taux(25), Eleve::isBoursier);

        assertThat(tauxRespecte).isTrue();
    }

    @Test
    public void indique_que_le_taux_n_est_pas_respecte_pour_un_taux_limite() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier());

        boolean tauxRespecte = ordreAppel.respecte(new Taux(34), Eleve::isBoursier);

        assertThat(tauxRespecte).isFalse();
    }

    @Test
    public void ajoute_un_eleve_en_dernier_a_l_ordre() {
        Eleve premierSelectionne = Eleve.nonBoursier();
        Eleve deuxiemeSelectionne = Eleve.boursier();
        OrdreAppel ordreAppel = OrdreAppel.de(premierSelectionne, deuxiemeSelectionne);

        Eleve nouveauSelectionne = Eleve.nonBoursier();
        OrdreAppel nouvelOrdreAppel = ordreAppel.ajouter(nouveauSelectionne);

        assertThat(nouvelOrdreAppel).isEqualTo(OrdreAppel.de(premierSelectionne, deuxiemeSelectionne, nouveauSelectionne));
    }
}