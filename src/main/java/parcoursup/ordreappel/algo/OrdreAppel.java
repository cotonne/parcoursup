/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parcoursup.ordreappel.algo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author gimbert
 */
public class OrdreAppel {

    /* la liste des voeux, dans l'ordre d'appel */
    public List<VoeuClasse> voeux = new LinkedList<>();

    public static Postulants de(Eleve eleve) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrdreAppel that = (OrdreAppel) o;
        return Objects.equals(voeux, that.voeux);
    }

    @Override
    public int hashCode() {

        return Objects.hash(voeux);
    }

    /* calcule une mesure de la différence entre le classement original et l'ordre d'appel:
        le nombre d'inversions ramené au nombre maximal d'inversions.
        Le nombre maximal d'inversions est obtenu si le classement est totalement inversé
        (cas hypothétique), auqel cas il y a autant d'inversions que de paires non-ordonnées
        de candidat c'est-à-dire n * (n - 1) / 2.
         */
    public double coefficientDivergence() {

        if (voeux.size() <= 1) {
            return 0.0f;
        }

        /* calcul du coefficient de divergence */
        int nbInversions = 0;
        for (VoeuClasse voe1 : voeux) {
            for (VoeuClasse voe2 : voeux) {
                if (voe2 == voe1) {
                    break;
                }
                if (voe2.rang > voe1.rang) {
                    nbInversions++;
                }
            }
        }

        return (2.0f * nbInversions)
                / (voeux.size() * (voeux.size() - 1));

    }
}
