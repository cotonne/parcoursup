package parcoursup.ordreappel.algo;

public enum StatusBourse implements StatusAvecPriorite {
    BOURSIER, NON_BOURSIER;

    @Override
    public StatusAvecPriorite nonPrioritaire() {
        return NON_BOURSIER;
    }
}
