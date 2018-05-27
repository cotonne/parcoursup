package parcoursup.ordreappel.algo;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.generator.InRange;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnitQuickcheck.class)
public class CommissionExamenPropertyTest {
    // Tests d'acceptances définis dans l'annexe A.1 de presentation_algorithmes_parcoursup.pdf
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20% => Ordre d'appel : [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [B1, C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12], 4 places, qB = 20%, refus de B1 => [C2, C3, C4, C5, C6, B7, C8, C9, C10, C11, C12]
    // [C1, C2, C3, C4, C5, B6, C7, C8, C9, C10, C11], 1 place, qB = 2% => [B6]
    // [C1 C2 C3 ...C900 B901 B902 B903 ...B1000 C1001 C1002 ....], 100 places, qB = 10% => [B901 C1 C2 C3 ...C9 B902 C10 C11 ...C18 B903 ...] (propositionAdmission.pourcentageBoursier >= qB)
    // [C1 B2 B3 C4 C5 C6 C7 B8 C9 C10], 1 place, qB = 20% => Ordre d'appel [B2 C1 B3 C4 C5 C6 C7 B8 C9 C10] [ne fait jamais reculer le classement d’un boursier]

    @Property
    public void pour_tout_k_au_moins_q_B_x_k_des_candidats_C1_a_Ck_sont_boursiers(
            @From(ClassementPedagogiqueGenerator.class) ClassementPedagogique classementPedagogique,
            @InRange(min = "1", max = "100") int tauxEnPourcentage) {
        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerParCritères(classementPedagogique, new TauxBoursier(tauxEnPourcentage));

        int i = 0;
        long nombre_boursiers_total = classementPedagogique.eleves.stream().filter(Eleve::isBoursier).count();
        long nombre_de_boursiers_trouves = 0;
        List<Eleve> eleves = ordreAppel.eleves;
        while (!(i >= eleves.size() || nombre_de_boursiers_trouves == nombre_boursiers_total)) {
            Eleve e = eleves.get(i);
            if (e.isBoursier()) {
                nombre_de_boursiers_trouves++;
            }

            i++;
            long tauxCourant = (nombre_de_boursiers_trouves * 100) / (i * 100);
            assertThat(tauxCourant).isLessThanOrEqualTo(tauxEnPourcentage);
        }
    }

    @Property
    public void Un_candidat_boursier_qui_a_le_rang_r_dans_le_classement_pédagogique_n_est_jamais_doublé_par_personne_et_aura_donc_un_rang_inférieur_ou_égal_à_r_dans_l_ordre_d_appel(
            @From(ClassementPedagogiqueGenerator.class) ClassementPedagogique classementPedagogique,
            @InRange(min = "0", max = "100") int tauxEnPourcentage) {
        Formation formation = new Formation(3);
        ComissionExamen commisionExamen = new ComissionExamen(formation);

        OrdreAppel ordreAppel = commisionExamen.ordonnerParCritères(classementPedagogique, new TauxBoursier(tauxEnPourcentage));

        Map<Eleve, Integer> classementPedagogiqueEleves = zipWithIndex(classementPedagogique.eleves.toArray(new Eleve[0]));
        Map<Eleve, Integer> ordreAppelEleves = zipWithIndex(ordreAppel.eleves.toArray(new Eleve[0]));
        List<Triple<Eleve, Integer, Integer>> rangs = grouper(classementPedagogiqueEleves, ordreAppelEleves);


        List<Triple<Eleve, Integer, Integer>> rangsElevesBoursiers = rangs.stream().filter(e -> e.getLeft().isBoursier()).collect(Collectors.toList());
        for (Triple<Eleve, Integer, Integer> rang : rangsElevesBoursiers) {
            List<Triple<Eleve, Integer, Integer>> elevesAvant = rangs.stream()
                    // Tous les eleves qui sont classes avant dans le nouveau classement
                    .filter(e -> e.getRight() < rang.getRight())
                    // Aucun n'était classé apres dans l'ancien classement
                    .filter(e -> e.getMiddle() > rang.getMiddle())
                    .collect(Collectors.toList());
            assertThat(elevesAvant).isEmpty();
        }
    }

    private List<Triple<Eleve, Integer, Integer>> grouper(Map<Eleve, Integer> classementPedagogiqueEleves, Map<Eleve, Integer> ordreAppelEleves) {
        List<Triple<Eleve, Integer, Integer>> result = new ArrayList<>();
        for (Map.Entry<Eleve, Integer> e : classementPedagogiqueEleves.entrySet()) {
            result.add(Triple.of(e.getKey(), e.getValue(), ordreAppelEleves.get(e.getKey())));
        }
        return result;
    }

    private <T> Map<T, Integer> zipWithIndex(T[] array) {
        List<Pair<Integer, T>> collect = IntStream.range(0, array.length)
                .mapToObj(i -> Pair.of(i, array[i]))
                .collect(Collectors.toList());
        Map<T, Integer> result = new HashMap<>();
        for (Pair<Integer, T> p : collect) {
            result.put(p.getRight(), p.getLeft());
        }
        return result;
    }

}
