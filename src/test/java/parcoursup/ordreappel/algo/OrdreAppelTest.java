package parcoursup.ordreappel.algo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OrdreAppelTest {

    @Test
    public void calculer_le_pourcentage_de_boursiers() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier(), Eleve.nonBoursier());

        TauxBoursier pourcentageBoursiers = ordreAppel.calculerPourcentageBoursiers();

        assertThat(pourcentageBoursiers).isEqualTo(new TauxBoursier(25));
    }

    @Test
    public void calculer_le_pourcentage_de_boursiers_pour_un_ordre_vide() {
        OrdreAppel ordreAppel = OrdreAppel.de();

        TauxBoursier pourcentageBoursiers = ordreAppel.calculerPourcentageBoursiers();

        assertThat(pourcentageBoursiers).isEqualTo(TauxBoursier.ZERO);
    }

    @Test
    public void indique_que_le_taux_est_respecte() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier(), Eleve.nonBoursier());

        boolean tauxRespecte = ordreAppel.respecter(new TauxBoursier(25));

        assertThat(tauxRespecte).isTrue();
    }

    @Test
    public void indique_que_le_taux_n_est_pas_respecte_pour_un_taux_limite() {
        OrdreAppel ordreAppel = OrdreAppel.de(Eleve.nonBoursier(), Eleve.boursier(), Eleve.nonBoursier());

        boolean tauxRespecte = ordreAppel.respecter(new TauxBoursier(34));

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