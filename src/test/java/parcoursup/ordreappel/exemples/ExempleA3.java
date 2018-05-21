/* Copyright 2018, 2018 Hugo Gimbert (hugo.gimbert@enseignementsup.gouv.fr) 

    This file is part of Algorithmes-de-parcoursup.

    Algorithmes-de-parcoursup is free software: you can redistribute it and/or modify
    it under the terms of the Affero GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Algorithmes-de-parcoursup is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    Affero GNU General Public License for more details.

    You should have received a copy of the Affero GNU General Public License
    along with Algorithmes-de-parcoursup.  If not, see <http://www.gnu.org/licenses/>.

 */
package parcoursup.ordreappel.exemples;

import parcoursup.ordreappel.algo.GroupeClassement;
import parcoursup.ordreappel.algo.VoeuClasse;

public class ExempleA3 extends ExempleOrdreAppel {

    @Override
    String nom() {
        return "exemple_A3";
    }

    @Override
    GroupeClassement initialise() {

        GroupeClassement groupe = new GroupeClassement(0, 10, 0);

        /* C1 C2 C3 ...C900 B901 B902 B903 ...B1000 */
        for (int i = 1; i <= 900; i++) {
            groupe.ajouterVoeu(new VoeuClasse(i, i, false, false));
        }
        for (int i = 901; i <= 1000; i++) {
            groupe.ajouterVoeu(new VoeuClasse(i, i, true, false));
        }

        return groupe;

    }

}
