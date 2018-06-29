package parcoursup.ordreappel.algo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EleveTest {

    @Test
    void devrait_indiquer_que_deux_eleves_avec_les_memes_status_sont_egaux() {
        Eleve eleve1 = Eleve.boursier().nonResident();
        Eleve eleve2 = Eleve.boursier().nonResident();
        assertThat(eleve1.compareTo(eleve2)).isEqualTo(0);
    }

    @Test
    void devrait_indiquer_qu_un_eleve_boursier_est_plus_prioritaire_qu_un_non_boursier() {
        Eleve boursier = Eleve.boursier().nonResident();
        Eleve nonBoursier = Eleve.nonBoursier().nonResident();
        assertThat(boursier.compareTo(nonBoursier)).isLessThan(0);
    }

    @Test
    void devrait_indiquer_qu_un_eleve_non_boursier_est_moins_prioritaire_qu_un_boursier() {
        Eleve boursier = Eleve.boursier().nonResident();
        Eleve nonBoursier = Eleve.nonBoursier().nonResident();
        assertThat(nonBoursier.compareTo(boursier)).isGreaterThan(0);
    }


    @Test
    void devrait_indiquer_qu_un_eleve_resident_est_plus_prioritaire_qu_un_non_resident() {
        Eleve resident = Eleve.nonBoursier().resident();
        Eleve nonResident = Eleve.nonBoursier().nonResident();
        assertThat(resident.compareTo(nonResident)).isLessThan(0);
    }

    @Test
    void devrait_indiquer_qu_un_eleve_non_resident_est_moins_prioritaire_qu_un_resident() {
        Eleve resident = Eleve.nonBoursier().resident();
        Eleve nonResident = Eleve.nonBoursier().nonResident();
        assertThat(nonResident.compareTo(resident)).isGreaterThan(0);
    }
}