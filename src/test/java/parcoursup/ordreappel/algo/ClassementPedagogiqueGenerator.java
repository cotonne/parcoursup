package parcoursup.ordreappel.algo;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;

import java.util.ArrayList;
import java.util.List;

public class ClassementPedagogiqueGenerator extends Generator<ClassementPedagogique> {
    public ClassementPedagogiqueGenerator() {
        super(ClassementPedagogique.class);
    }

    @Override
    public ClassementPedagogique generate(SourceOfRandomness random, GenerationStatus status) {
        List<Eleve> eleves = new ArrayList<>();
        int taille = random.nextInt(1000);
        for (int i = 0; i < taille; i++) {
            boolean isBoursier = random.nextBoolean();
            eleves.add(isBoursier ? Eleve.boursier() : Eleve.nonBoursier());
        }
        return new ClassementPedagogique(eleves.toArray(new Eleve[0]));
    }
}
