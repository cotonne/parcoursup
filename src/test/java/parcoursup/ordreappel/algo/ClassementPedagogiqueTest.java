package parcoursup.ordreappel.algo;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClassementPedagogiqueTest {

    private static final List<StatusAvecPriorite> AUCUN_CONTRAINTE = Collections.emptyList();

    @Test
    void devrait_prendre_le_premier_eleve_quand_toutes_les_contraintes_sont_satisfaites() {
        Eleve eleve1 = Eleve.nonBoursier().resident();
        Eleve eleve2 = Eleve.nonBoursier().resident();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleve1, eleve2);
        Pair<Eleve, ClassementPedagogique> eleveSelectionne = classementPedagogique.prendreSuivantRespectant(AUCUN_CONTRAINTE);
        assertThat(eleveSelectionne).isEqualTo(Pair.of(eleve1, new ClassementPedagogique(eleve2)));
    }

    @Test
    void devrait_prendre_le_premier_eleve_boursier_quand_le_nombre_de_boursiers_est_insuffisant() {
        Eleve eleveNonBoursier = Eleve.nonBoursier().resident();
        Eleve eleveBoursier = Eleve.boursier().resident();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleveNonBoursier, eleveBoursier);
        Pair<Eleve, ClassementPedagogique> eleveSelectionne = classementPedagogique.prendreSuivantRespectant(Collections.singletonList(StatusBourse.BOURSIER));
        assertThat(eleveSelectionne).isEqualTo(Pair.of(eleveBoursier, new ClassementPedagogique(eleveNonBoursier)));
    }

    @Test
    void devrait_prendre_le_premier_eleve_resident_quand_le_nombre_de_resident_est_insuffisant() {
        Eleve eleveNonResident = Eleve.nonBoursier().nonResident();
        Eleve eleveResident = Eleve.nonBoursier().resident();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(eleveNonResident, eleveResident);
        Pair<Eleve, ClassementPedagogique> eleveSelectionne = classementPedagogique.prendreSuivantRespectant(Collections.singletonList(StatusResident.RESIDENT));
        assertThat(eleveSelectionne).isEqualTo(Pair.of(eleveResident, new ClassementPedagogique(eleveNonResident)));
    }

    @Test
    void devrait_respecter_les_contraintes_entre_priorites() {
        Eleve boursierResident = Eleve.boursier().resident();
        Eleve bousierNonResident = Eleve.boursier().nonResident();
        Eleve nonBoursierResident = Eleve.nonBoursier().resident();
        Eleve nonBoursierNonResident = Eleve.nonBoursier().nonResident();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(nonBoursierNonResident, bousierNonResident, nonBoursierResident, boursierResident);
        Pair<Eleve, ClassementPedagogique> Selectionne = classementPedagogique.prendreSuivantRespectant(Arrays.asList(StatusBourse.BOURSIER, StatusResident.RESIDENT));
        assertThat(Selectionne).isEqualTo(Pair.of(boursierResident, new ClassementPedagogique(nonBoursierNonResident, bousierNonResident, nonBoursierResident)));
    }

    @Test
    void devrait_prendre_le_premier_qui_satisfait_la_contrainte_bourse_s_il_n_y_a_plus_de_boursiers_residents() {
        Eleve bousierNonResident = Eleve.boursier().nonResident();
        Eleve nonBoursierResident = Eleve.nonBoursier().resident();
        Eleve nonBoursierNonResident = Eleve.nonBoursier().nonResident();
        ClassementPedagogique classementPedagogique = new ClassementPedagogique(nonBoursierNonResident, nonBoursierResident, bousierNonResident);
        Pair<Eleve, ClassementPedagogique> Selectionne = classementPedagogique.prendreSuivantRespectant(Arrays.asList(StatusBourse.BOURSIER, StatusResident.RESIDENT));
        assertThat(Selectionne).isEqualTo(Pair.of(bousierNonResident, new ClassementPedagogique(nonBoursierNonResident, nonBoursierResident)));
    }
}