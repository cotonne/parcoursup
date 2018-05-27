package parcoursup.ordreappel.algo;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public class ClassementPedagogique {
    public final List<Eleve> eleves;

    public ClassementPedagogique(Eleve... eleves) {
        this.eleves = Collections.unmodifiableList(Arrays.asList(eleves));
    }

    Groupe construire() {
        Map<StatusBourse, List<Eleve>> elevesParStatusBourse = eleves.stream()
                .collect(groupingBy(Eleve::getStatusBourse));
        final List<Eleve> boursiers = elevesParStatusBourse.getOrDefault(StatusBourse.BOURSIER, Collections.emptyList());
        final List<Eleve> nonBoursiers = elevesParStatusBourse.getOrDefault(StatusBourse.NON_BOURSIER, Collections.emptyList());
        return new Groupe(boursiers, nonBoursiers);
    }
}
